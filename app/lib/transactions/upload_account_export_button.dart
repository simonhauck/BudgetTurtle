import 'package:budget_turtle/router.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class UploadAccountExportButton extends StatelessWidget {
  const UploadAccountExportButton({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton.extended(
      label: const Text("Import"),
      icon: const Icon(Icons.add),
      onPressed: () => _onPressed(context),
    );
  }

  void _onPressed(BuildContext context) {
    context.push(importScreen);
  }
}
