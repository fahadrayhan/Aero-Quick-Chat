# AeroQuickChat

A modern Android application for quick WhatsApp messaging with enhanced UI/UX design and Material Design components.

## Features

- **Quick WhatsApp Integration**: Send messages directly to WhatsApp contacts without saving phone numbers
- **Modern Material Design**: Clean, intuitive interface following Material Design 3 guidelines with updated color palette
- **Automatic Country Detection**: Smart country code detection from phone number input with Bangladesh (+880) as default
- **Enhanced Typography**: Improved text hierarchy with system fonts for better readability
- **Smart Error Notifications**: Non-intrusive toast-style notifications with visual input field error styling
- **Premium WhatsApp Logo**: High-quality vector logo with gradient styling and modern design
- **Settings Management**: Customizable app settings and preferences with modern card-based design
- **Responsive Design**: Optimized for various screen sizes and orientations
- **Material Components**: Updated UI components with proper theming and consistent styling
- **Dynamic Theme Support**: Full dark/light mode support with Material Design 3 color schemes
- **Auto-dismiss Notifications**: Smart error handling with automatic dismissal and real-time input validation

## Screenshots

*Screenshots will be added here*

## Installation

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK API level 21 or higher
- Kotlin 1.8+
- Gradle 8.0+

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/AeroQuickChat.git
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the application

## Usage

1. **Enter Phone Number**: Input the recipient's phone number with country code (automatically detects country from number prefix)
2. **Smart Formatting**: Phone numbers are automatically formatted based on detected country code
3. **Compose Message**: Type your message in the text field (customizable in settings)
4. **Send via WhatsApp**: Tap the "Open WhatsApp" button to launch WhatsApp with your message

## Technical Details

### Architecture

- **Language**: Kotlin
- **UI Framework**: Android Views with Data Binding
- **Design System**: Material Design 3
- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)

### Key Components

- `MainActivity.kt`: Main application interface with phone number input and WhatsApp integration
- `SettingsActivity.kt`: Settings and preferences management with modern card-based layout
- `ErrorHandler.kt`: Centralized error handling with toast-style notifications and Play Store integration
- `toast_error.xml`: Modern error notification layout with Material Design cards
- `WhatsAppHelper.kt`: WhatsApp integration utilities for seamless message sending
- `themes.xml`: Material Design 3 theming with custom color palette and typography
- `colors.xml`: Modern color scheme with primary, secondary, and accent colors

### Dependencies

- Material Design Components
- AndroidX Libraries
- Data Binding
- ViewBinding

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/aeroquickchat/
│   │   ├── MainActivity.kt
│   │   ├── SettingsActivity.kt
│   │   ├── ErrorHandler.kt
│   │   └── WhatsAppHelper.kt
│   ├── res/
│   │   ├── layout/
│   │   ├── values/
│   │   ├── drawable/
│   │   └── mipmap/
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Recent Updates

- ✅ **Dynamic Theme Support**: Complete dark/light mode implementation with Material Design 3 colors
- ✅ **Automatic Country Detection**: Smart country code detection replacing manual dropdown selection
- ✅ **Toast-Style Notifications**: Replaced full-page error dialogs with elegant top notifications
- ✅ **Enhanced WhatsApp Logo**: Premium vector logo with gradient styling and 48dp size
- ✅ **Auto-dismiss Errors**: Smart notification system with 5-second auto-dismiss for non-critical errors
- ✅ **Improved Phone Formatting**: Country-specific phone number validation and formatting with auto-detection
- ✅ **Material Design 3 Colors**: Complete color scheme overhaul for both light and dark themes
- ✅ **Error Input Styling**: Phone number input field turns red with visual feedback on validation errors
- ✅ **Dark Mode Text Fix**: Proper text color adaptation for about page in dark mode

## Roadmap

- [x] ~~Multi-country support expansion~~ ✅ **Completed**
- [x] ~~Custom vector icons and improved iconography~~ ✅ **Completed**
- [x] ~~Dark mode support with dynamic theming~~ ✅ **Completed**
- [x] ~~Automatic country detection system~~ ✅ **Completed**
- [x] ~~Input validation with visual error feedback~~ ✅ **Completed**
- [ ] Gradient backgrounds and visual effects
- [ ] Micro-animations and smooth transitions
- [ ] Message templates and quick replies
- [ ] Contact management integration
- [ ] Message history and favorites
- [ ] Additional country support (beyond current 9 countries)
- [ ] Custom notification sounds and vibration patterns

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Material Design team for design guidelines
- Android development community
- WhatsApp for providing URL scheme integration

## Support

If you encounter any issues or have questions, please:

1. Check the [Issues](https://github.com/yourusername/AeroQuickChat/issues) page
2. Create a new issue if your problem isn't already reported
3. Provide detailed information about your environment and the issue

---

**Made with ❤️ for seamless WhatsApp communication**