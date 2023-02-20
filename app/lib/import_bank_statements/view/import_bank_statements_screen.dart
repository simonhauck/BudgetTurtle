import 'package:budget_turtle/import_bank_statements/view/selected_files_list.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';

class ImportBankStatementsScreen extends StatefulWidget {
  const ImportBankStatementsScreen({Key? key}) : super(key: key);

  @override
  State<ImportBankStatementsScreen> createState() =>
      _ImportBankStatementsScreenState();
}

class _ImportBankStatementsScreenState
    extends State<ImportBankStatementsScreen> {
  List<PlatformFile> selectedFiles = [];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Import")),
      body: Column(
        children: [_body(), _actionButtons()],
      ),
    );
  }

  Widget _body() {
    return SelectedFilesList(
      files: selectedFiles,
      onSelectFiles: () => _selectFiles(),
    );
  }

  Widget _actionButtons() {
    return Column(
      children: [
        ElevatedButton(
            onPressed: () => _selectFiles(), child: const Text("Select files"))
      ],
    );
  }

  Future<void> _selectFiles() async {
    FilePickerResult? result = await FilePicker.platform.pickFiles(
        dialogTitle: "Select bank statements",
        allowedExtensions: ["csv"],
        type: FileType.custom,
        allowMultiple: true);

    if (result == null) return;

    setState(() {
      selectedFiles.addAll(result.files);
    });
  }
}
