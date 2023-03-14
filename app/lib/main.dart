import 'package:budget_turtle/router.dart';
import 'package:budget_turtle/user/user.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(MultiProvider(
    providers: [
      Provider(
        create: (_) => User("otherTest"),
      )
    ],
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'Budget Turtle',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routerConfig: getRouter(),
    );
  }
}
