import 'package:budget_turtle/import_bank_statements/view/import_bank_statements_screen.dart';
import 'package:budget_turtle/transactions/transaction_screen.dart';
import 'package:go_router/go_router.dart';

const transactionScreen = "/";
const importScreen = "/import";

final GoRouter _router = GoRouter(
  initialLocation: "/",
  routes: [
    GoRoute(
      path: transactionScreen,
      builder: (context, state) => const TransactionScreen(),
    ),
    GoRoute(
      path: importScreen,
      builder: (context, state) => const ImportBankStatementsScreen(),
    ),
  ],
);

GoRouter getRouter() => _router;
