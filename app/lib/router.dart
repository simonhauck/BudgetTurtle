import 'package:budget_turtle/import_bank_statements/view/import_bank_statements_screen.dart';
import 'package:budget_turtle/main.dart';
import 'package:go_router/go_router.dart';

const homeScreen = "/";
const importScreen = "/import";

final GoRouter _router = GoRouter(
  initialLocation: "/",
  routes: [
    GoRoute(
      path: homeScreen,
      builder: (context, state) => const MyHomePage(title: "Some title"),
    ),
    GoRoute(
      path: importScreen,
      builder: (context, state) => const ImportBankStatementsScreen(),
    ),
  ],
);

GoRouter getRouter() => _router;
