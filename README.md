# AsDiff

AsDiff is an Android Studio plugin for comparing two pasted text values with the IDE's native Diff Viewer.

[Source code](https://github.com/KafuuNeko/AsDiff) | [MIT License](LICENSE)

## Features

- Two resizable, theme-aware editor panes with line numbers, paste, undo, and find support
- Native line and inline diff highlighting, synchronized scrolling, and change navigation
- Swap and clear controls
- Project-level input persistence between dialog openings
- `Tools > Compare Text...` menu action
- `Compare Selected Text (Auto-format JSON)` submenu in the editor context menu
- Use the selection as Left while keeping the previous Right text, or load Right from the text clipboard
- Automatic pretty-printing when either side contains a valid JSON object or array
- `Ctrl+Alt+Shift+D` shortcut on Windows/Linux and `Control+Option+Shift+D` on macOS

The comparison stays local. AsDiff does not send either input to a network service.

## Development

The plugin requires IntelliJ Platform build `261` or newer, with no upper build
limit. The compile target remains `AI-261` to preserve compatibility with the
oldest supported platform. Future IDE releases are not blocked by their version
number; API compatibility still needs verification against each target release.

Requirements:

- Android Studio 2026.1.x (`AI-261`)
- JDK 21 or newer

The default target is `/Applications/Android Studio.app`. Override it when needed:

```bash
./gradlew build -PandroidStudioPath="/path/to/Android Studio.app"
```

Useful tasks:

```bash
./gradlew test
./gradlew buildPlugin
./gradlew runIde
```

The installable ZIP is generated under `build/distributions/`.

To update an installed copy, open **Settings > Plugins > gear icon > Install Plugin from Disk...**,
select the new ZIP, and restart the IDE. Version 1.2.1 removes the compatibility
upper bound of `261.*`; the previously installed 1.2.0 must be updated
before Android Studio's updater can recognize this compatibility change.

## Use

1. Open an Android Studio project.
2. Choose `Tools > Compare Text...`, or select editor text and open `Compare Selected Text (Auto-format JSON)` from the context menu.
3. Choose whether to keep the previous Right text or load Right from the text clipboard.
4. Edit either side if needed, then select **Compare** to open the native Diff Viewer.

Inputs are stored in the current project's workspace settings so the dialog can be reopened without losing work.

## License

AsDiff is available under the MIT License.
