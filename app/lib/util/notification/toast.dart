import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

extension ToastNotifications on FToast {
  showSuccess(String message) {
    Widget toast = _buildToastWidget(message, Colors.greenAccent, Icons.check);

    showToast(child: toast);
  }

  showError(String message) {
    Widget toast = _buildToastWidget(message, Colors.redAccent, Icons.error);

    showToast(child: toast);
  }

  Widget _buildToastWidget(
      String message, Color backgroundColor, IconData icon) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 24.0, vertical: 12.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(25.0),
        color: backgroundColor,
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon),
          const SizedBox(
            width: 12.0,
          ),
          Text(message),
        ],
      ),
    );
  }
}
