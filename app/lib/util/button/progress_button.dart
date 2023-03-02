import 'package:flutter/material.dart';

class ProgressButton extends StatelessWidget {
  final String label;
  final bool isLoading;
  final Function onPressed;

  const ProgressButton({
    super.key,
    required this.label,
    required this.isLoading,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return ElevatedButton.icon(
        icon: Container(
          width: 24,
          height: 24,
          padding: const EdgeInsets.all(6.0),
          child: CircularProgressIndicator(
            color: Theme.of(context).colorScheme.onPrimary,
            strokeWidth: 2.0,
          ),
        ),
        onPressed: null,
        label: Text(label),
      );
    }
    return ElevatedButton(onPressed: () => onPressed(), child: Text(label));
  }
}
