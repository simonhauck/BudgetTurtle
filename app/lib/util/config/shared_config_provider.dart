import 'package:budget_turtle/user/user.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class SharedConfigProvider extends StatelessWidget {
  final Widget Function(User) buildChild;

  const SharedConfigProvider({Key? key, required this.buildChild})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    var user = context.watch<User>();
    return buildChild(user);
  }
}
