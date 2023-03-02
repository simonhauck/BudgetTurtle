import 'package:budget_turtle/import_bank_statements/layout_constants.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';

class SelectedFilesList extends StatelessWidget {
  final List<PlatformFile> files;
  final Function onSelectFiles;

  const SelectedFilesList(
      {Key? key, required this.files, required this.onSelectFiles})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: files.isEmpty ? _emptyListBody(context) : _listWithFileItems(),
    );
  }

  Widget _listWithFileItems() {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Padding(
          padding: const EdgeInsets.all(LayoutConstants.paddingMedium),
          child: ListView.builder(
            shrinkWrap: true,
            itemBuilder: (context, index) => _displayFileItem(index),
            itemCount: files.length,
          ),
        ),
        Padding(
          padding: const EdgeInsets.only(top: LayoutConstants.paddingMedium),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("You have selected ${files.length} file(s)."),
              TextButton(
                onPressed: () => onSelectFiles(),
                child: const Text("Select more?"),
              )
            ],
          ),
        )
      ],
    );
  }

  Container _emptyListBody(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            "You have to select bank account statements before you can continue",
            textAlign: TextAlign.center,
            style: Theme.of(context).textTheme.bodyMedium,
          ),
          TextButton(
            onPressed: () => onSelectFiles(),
            child: const Text("Select files"),
          ),
        ],
      ),
    );
  }

  Widget _displayFileItem(int index) {
    var file = files[index];
    return ListTile(
      leading: const Icon(Icons.file_present),
      title: Text(file.name),
    );
  }
}
