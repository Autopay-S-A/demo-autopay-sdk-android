# preserve the line number information for debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# hide the original source file name.
-renamesourcefileattribute SourceFile

-keep public class eu.autopay.pay.demo.ui.type.DemoType { *; }
-keep public class eu.autopay.pay.demo.ui.style.StyleHolder$Palette { *; }
-keep public class eu.autopay.pay.demo.ui.utils.DemoConfig { *; }