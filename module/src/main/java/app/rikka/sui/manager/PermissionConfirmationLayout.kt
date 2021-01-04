package app.rikka.sui.manager

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import app.rikka.sui.ktx.resolveColorStateList
import app.rikka.sui.ktx.resolveDrawable
import kotlin.math.roundToInt

class PermissionConfirmationLayout(context: Context, label: String) {

    val root: LinearLayout
    val title: TextView
    val allowButton: TextView
    val onetimeButton: TextView
    val denyButton: TextView
    //val background: Drawable

    private val density = context.resources.displayMetrics.density
    private val theme = context.theme
    private val isNight = theme.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES != 0

    private val divider = ColorDrawable(0x1f000000 or (if (isNight) 0xffffff else 0))
    private val textColorPrimary = theme.resolveColorStateList(android.R.attr.textColorPrimary)
    private val colorControlActivated = theme.resolveColorStateList(android.R.attr.colorControlActivated)
    private val l7 = (56 * density).roundToInt()
    private val l3 = (24 * density).roundToInt()
    private val l2 = (16 * density).roundToInt()
    private val l1 = 8 * density
    private val l_125 = (1 * density).roundToInt()
    private val sansSerifMedium = Typeface.create("sans-serif-medium", Typeface.NORMAL)

    init {
        /*val shapeDrawable = ShapeDrawable()
        shapeDrawable.shape = RoundRectShape(floatArrayOf(l1, l1, l1, l1, l1, l1, l1, l1), null, null)
        shapeDrawable.paint.color = theme.resolveColor(android.R.attr.colorBackground)
        val insetDrawable = InsetDrawable(shapeDrawable, l2, l2, l2, l2)
        background = insetDrawable*/

        root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            //elevation = l1
        }

        ImageView(context).apply {
            imageTintMode = PorterDuff.Mode.SRC_IN
            imageTintList = colorControlActivated
            setImageDrawable(VectorDrawableCreator.getVectorDrawable(
                    context, 24, 24, 24f, 24f,
                    listOf(VectorDrawableCreator.PathData("M3,5A2,2 0 0,1 5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5C3.89,21 3,20.1 3,19V5M7,18H9L9.35,16H13.35L13,18H15L15.35,16H17.35L17.71,14H15.71L16.41,10H18.41L18.76,8H16.76L17.12,6H15.12L14.76,8H10.76L11.12,6H9.12L8.76,8H6.76L6.41,10H8.41L7.71,14H5.71L5.35,16H7.35L7,18M10.41,10H14.41L13.71,14H9.71L10.41,10Z", Color.WHITE))))
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }.also {
            root.addView(it, LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
                topMargin = l3
            })
        }

        title = TextView(context).apply {
            setPadding(l3, l1.roundToInt(), l3, l3)
            gravity = Gravity.CENTER
            setTextColor(textColorPrimary)
            typeface = sansSerifMedium
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            text = Html.fromHtml("Allow <font face=\"sans-serif\"><b>$label</b></font> to have full access of the device?")
        }.also {
            root.addView(it, LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
            root.addDivider(divider, l_125)
        }

        allowButton = root.addButton("Allow all the time")
        root.addDivider(divider, l_125)

        onetimeButton = root.addButton("Only this time")
        root.addDivider(divider, l_125)

        denyButton = root.addButton("Deny")
    }

    private fun LinearLayout.addDivider(divider: Drawable?, height: Int) = View(context).apply {
        background = divider
    }.also {
        addView(it, LinearLayout.LayoutParams(MATCH_PARENT, height))
    }

    private fun LinearLayout.addButton(text: CharSequence) = TextView(context).apply {
        background = theme.resolveDrawable(android.R.attr.selectableItemBackground)
        typeface = sansSerifMedium
        minHeight = l7
        setPadding(l2, l2, l2, l2)
        gravity = Gravity.CENTER
        setTextColor(colorControlActivated)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        setText(text)
    }.also {
        addView(it, LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }
}