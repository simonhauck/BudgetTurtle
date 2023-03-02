import 'package:budget_turtle/transactions/infinite_transaction_list.dart';
import 'package:budget_turtle/transactions/upload_account_export_button.dart';
import 'package:budget_turtle/user/user.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class TransactionScreen extends StatelessWidget {
  const TransactionScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var user = context.watch<User>();
    return Scaffold(
      appBar: AppBar(
        title: const Text("Transactions"),
      ),
      floatingActionButton: const UploadAccountExportButton(),
      body: InfiniteTransactionList(userId: user.identifier),
    );
  }
}
