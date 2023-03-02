import 'dart:convert';

import 'package:budget_turtle/config.dart';
import 'package:budget_turtle/import_bank_statements/view/selected_files_list.dart';
import 'package:budget_turtle/user/user.dart';
import 'package:budget_turtle/util/button/progress_button.dart';
import 'package:budget_turtle/util/notification/toast.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:provider/provider.dart';
import 'package:server/server.dart';

class ImportBankStatementsScreen extends StatefulWidget {
  const ImportBankStatementsScreen({Key? key}) : super(key: key);

  @override
  State<ImportBankStatementsScreen> createState() =>
      _ImportBankStatementsScreenState();
}

class _ImportBankStatementsScreenState
    extends State<ImportBankStatementsScreen> {
  List<PlatformFile> selectedFiles = [];

  final _importApi = Server(basePathOverride: getBasePath())
      .getTransactionImportControllerApi();
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
        allowMultiple: true,
        withData: true);

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

    var user = context.read<User>();

    for (var nativeFile in selectedFiles) {
      var content = base64Encode(nativeFile.bytes?.toList() ?? []);

      await _handleFileUpload(nativeFile.name, content, user);
    }

    setState(() {
      isLoading = false;
    });
  }

  Future<void> _handleFileUpload(String name, String content, User user) async {
    var dto = EncodedFileDtoBuilder()
      ..name = name
      ..base64Content = content;

    try {
      var importBankStatementCsv = await _importApi.importBankStatementCsv(
          userId: user.identifier, encodedFileDto: dto.build());

      if (importBankStatementCsv.data?.success ?? false) {
        fToast.showSuccess("Successfully imported file $name");
      } else {
        var message = "Error ${importBankStatementCsv.data?.errorMsg}";
        fToast.showError(message);
      }
    } catch (e) {
      fToast.showError("Upload failed with an unexpected error");
    }
  }
}
