import 'package:budget_turtle/config.dart';
import 'package:flutter/material.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';
import 'package:server/server.dart';

class InfiniteTransactionList extends StatefulWidget {
  final String userId;

  const InfiniteTransactionList({required this.userId, Key? key})
      : super(key: key);

  @override
  State<InfiniteTransactionList> createState() =>
      _InfiniteTransactionListState();
}

class _InfiniteTransactionListState extends State<InfiniteTransactionList> {
  final PagingController<String?, TransactionDto> _pagingController =
      PagingController(firstPageKey: null);

  @override
  void initState() {
    super.initState();
    _pagingController.addPageRequestListener((pageKey) {
      _fetchPage(pageKey);
    });
  }

  @override
  Widget build(BuildContext context) {
    return PagedListView(
        pagingController: _pagingController,
        builderDelegate: PagedChildBuilderDelegate(
          itemBuilder: _buildTransactionDtoItem,
        ));
  }

  void _fetchPage(String? pageKey) async {
    var transactionControllerApi =
        Server(basePathOverride: getBasePath()).getTransactionControllerApi();
    try {
      var response = await transactionControllerApi.getTransactionPage(
          userId: widget.userId, lastSeenID: pageKey);

      var data = response.data;
      if (data == null) {
        return;
      }

      var items = data.transactions.toList();
      if (data.canRequestMore) {
        print(data.canRequestMore);
        _pagingController.appendPage(items, data.transactions.last.id);
        return;
      }

      print("can not append more");
      _pagingController.appendLastPage(items);
    } catch (e) {
      _pagingController.error = e;
    }
  }

  Widget _buildTransactionDtoItem(
      BuildContext context, TransactionDto item, int index) {
    return ListTile(
      title: Text(item.details.clientName),
      subtitle: Text(item.details.purpose),
    );
  }
}
