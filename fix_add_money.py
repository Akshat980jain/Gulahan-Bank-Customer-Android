import re

with open("app/src/main/java/com/example/ui/screens/AddMoneyScreen.kt", "r") as f:
    content = f.read()

# Replace file contents to include VisualTransformation and HapticFeedback
new_imports = """import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat"""

if "import androidx.compose.ui.hapticfeedback.HapticFeedbackType" not in content:
    content = content.replace("import androidx.compose.ui.unit.sp", "import androidx.compose.ui.unit.sp\n" + new_imports)

# Add CurrencyVisualTransformation if not present
transformation_code = """
class AddMoneyCurrencyTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        if (originalText.isEmpty()) return TransformedText(text, OffsetMapping.Identity)
        
        val formatted = try {
            val parsed = originalText.toLong()
            DecimalFormat("#,###").format(parsed)
        } catch (e: Exception) {
            originalText
        }
        
        return TransformedText(
            AnnotatedString(formatted),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (originalText.isEmpty()) return 0
                    val totalCommas = Math.max(0, (originalText.length - 1) / 3)
                    val offsetFromRight = originalText.length - offset
                    val commasAfterOffset = Math.max(0, (offsetFromRight - 1) / 3)
                    return offset + (totalCommas - commasAfterOffset)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var originalOffset = 0
                    var transformedIndex = 0
                    while (transformedIndex < offset && originalOffset < originalText.length) {
                        if (formatted[transformedIndex] != ',') {
                            originalOffset++
                        }
                        transformedIndex++
                    }
                    return originalOffset
                }
            }
        )
    }
}
"""
if "class AddMoneyCurrencyTransformation" not in content:
    content = content.replace("@OptIn(ExperimentalMaterial3Api::class)", transformation_code + "\n@OptIn(ExperimentalMaterial3Api::class)")

# Add haptic feedback and mask to TextField
content = content.replace("var amount by remember { mutableStateOf(\"\") }", "var amount by remember { mutableStateOf(\"\") }\n    val haptic = LocalHapticFeedback.current")

content = content.replace("IconButton(onClick = onNavigateBack)", """IconButton(onClick = { 
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onNavigateBack() 
                    })""")

content = content.replace("onValueChange = { amount = it },", "onValueChange = { if (it.all { char -> char.isDigit() }) amount = it },\n                visualTransformation = AddMoneyCurrencyTransformation(),")

content = content.replace('onClick = onNavigateSuccess,', 'onClick = {\n                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)\n                    onNavigateSuccess()\n                },')

# Update quick buttons
content = content.replace('QuickAmountButton("₹1000", Modifier.weight(1f)) { amount = "1000" }', 'QuickAmountButton("₹1000", Modifier.weight(1f)) { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove); amount = "1000" }')
content = content.replace('QuickAmountButton("₹2000", Modifier.weight(1f)) { amount = "2000" }', 'QuickAmountButton("₹2000", Modifier.weight(1f)) { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove); amount = "2000" }')
content = content.replace('QuickAmountButton("₹5000", Modifier.weight(1f)) { amount = "5000" }', 'QuickAmountButton("₹5000", Modifier.weight(1f)) { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove); amount = "5000" }')

with open("app/src/main/java/com/example/ui/screens/AddMoneyScreen.kt", "w") as f:
    f.write(content)
