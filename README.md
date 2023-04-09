<h1 align="center">
	Manuscript
</h1>
<h2 align="center">
	Frontend workshop / component library for Jetpack Compose
</h2>
<p align="center">
	Heavily inspired by the excellent <a href="https://github.com/storybookjs/storybook">Storybook</a>
</p>

## Getting Started

Simply add the dependency to your `build.gradle(.kts) file: ![Maven Central](https://img.shields.io/maven-central/v/io.ezard.manuscript/manuscript)

```kotlin
implementation("io.ezard.manuscript:manuscript:<latest-version>")
ksp("io.ezard.manuscript:ksp:<latest-version>")
```

## Documentation

API docs can be found here: https://ezard.github.io/manuscript/

## Usage

### Components

```kotlin
@Composable
fun Button(text: String, colour: Color, enabled: Boolean, onClick: () -> Unit = {}) {
    Text(
        text = text,
        modifier = Modifier
            .background(colour.copy(alpha = if (enabled) 1f else 0.5f))
            .padding(16.dp)
            .clickable(enabled = enabled, onClick = onClick),
    )
}

@Preview
@Composable
fun ButtonPreview() {
    Manuscript {
        val text1 by control("Text", "Foo")
        val text2 by control(name = "Nullable thing", defaultValue = "Bar")
        val text2Null by control(name = "Is Nullable thing null", defaultValue = false)
        val onClick = action(name = "onClick")

        val combinedText = "$text1 ${if (text2Null) "<null>" else text2}"

        variant("Red") {
            Button(
                text = combinedText,
                colour = Color.Red,
                enabled = true,
                onClick = { onClick.trigger() },
            )
        }
        variant("Green") {
            Button(
                text = combinedText,
                colour = Color.Green,
                enabled = true,
                onClick = { onClick.trigger() },
            )
        }
    }
}
```

### Library

```kotlin
ManuscriptLibrary {
    Group(name = "Buttons") {
        Component(name = "Rectangular Button") {
            RectangularButtonPreview()
        }
        Component(name = "Circular Button") {
            CircularButtonPreview()
        }
    }
    Group(name = "Charts") {
        Component(name = "Bar Chart") {
            BarChartPreview()
        }
        Component(name = "Line Chart") {
            LineChartPreview()
        }
        Component(name = "Pie Chart") {
            PieChartPreview()
        }
    }
}
```

## Alternatives

Don't need the ability to change data on the fly and see actions? Just want an auto-generated component library? [Showkase](https://github.com/airbnb/Showkase) might fit your needs better (or just make use of both libraries!)