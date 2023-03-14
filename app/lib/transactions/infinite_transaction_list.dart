import 'package:flutter/material.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';
import 'package:server/server.dart';

class InfiniteTransactionList extends StatefulWidget {
  final PagingController<String?, TransactionDto> pagingController;

  const InfiniteTransactionList({required this.pagingController, Key? key})
      : super(key: key);

  @override
  State<InfiniteTransactionList> createState() =>
      _InfiniteTransactionListState();
}

class _InfiniteTransactionListState extends State<InfiniteTransactionList> {
  @override
  Widget build(BuildContext context) {
    return PagedListView(
        pagingController: widget.pagingController,
        builderDelegate: PagedChildBuilderDelegate(
          itemBuilder: _buildTransactionDtoItem,
        ));
  }

  Widget _buildTransactionDtoItem(
      BuildContext context, TransactionDto item, int index) {
    return ListTile(
      title: Text(item.details.clientName),
      subtitle: Text(item.details.purpose),
    );
  }
}
