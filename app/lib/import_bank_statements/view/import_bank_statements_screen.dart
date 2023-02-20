import 'package:budget_turtle/util/button/progress_button.dart';
import 'package:budget_turtle/import_bank_statements/view/selected_files_list.dart';
import 'package:budget_turtle/util/notification/toast.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class ImportBankStatementsScreen extends StatefulWidget {
  const ImportBankStatementsScreen({Key? key}) : super(key: key);

  @override
  State<ImportBankStatementsScreen> createState() =>
      _ImportBankStatementsScreenState();
}

class _ImportBankStatementsScreenState
    extends State<ImportBankStatementsScreen> {
  List<PlatformFile> selectedFiles = [];

  bool isLoading = false;

  FToast fToast = FToast();

  @override
  void initState() {
    super.initState();
    fToast.init(context);
  }

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
        ProgressButton(
          onPressed: _importFiles,
          label: "Import files",
          isLoading: isLoading,
        ),
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

  Future<void> _importFiles() async {
    setState(() {
      isLoading = true;
    });
    if (selectedFiles.isEmpty) {
      fToast.showError("You have to select some files");
    }

    setState(() {
      isLoading = false;
    });
  }
}
