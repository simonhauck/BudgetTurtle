import 'package:budget_turtle/transactions/infinite_transaction_list.dart';
import 'package:budget_turtle/transactions/upload_account_export_button.dart';
import 'package:budget_turtle/user/user.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';
import 'package:server/server.dart';

import '../config.dart';

class TransactionScreen extends StatefulWidget {
  final User user;

  const TransactionScreen({Key? key, required this.user}) : super(key: key);

  @override
  State<TransactionScreen> createState() => _TransactionScreenState();
}

class _TransactionScreenState extends State<TransactionScreen> {
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
    return Scaffold(
      appBar: AppBar(
        title: const Text("Transactions"),
      ),
      floatingActionButton: UploadAccountExportButton(
        onNavigationChanged: () => addRouteListener(context),
      ),
      body: InfiniteTransactionList(pagingController: _pagingController),
    );
  }

  void _fetchPage(String? pageKey) async {
    var transactionControllerApi =
        Server(basePathOverride: getBasePath()).getTransactionControllerApi();

    var id = widget.user.identifier;

    try {
      var response = await transactionControllerApi.getTransactionPage(
          userId: id, lastSeenID: pageKey);

      var data = response.data;
      if (data == null) {
        return;
      }

      var items = data.transactions.toList();
      if (data.canRequestMore) {
        _pagingController.appendPage(items, data.transactions.last.id);
        return;
      }

      _pagingController.appendLastPage(items);
    } catch (e) {
      _pagingController.error = e;
    }
  }

  void addRouteListener(BuildContext context) =>
      GoRouter.of(context).addListener(watchRouteChange);

  watchRouteChange() {
    GoRouter.of(context).removeListener(watchRouteChange);
    _pagingController.refresh();
  }
}
