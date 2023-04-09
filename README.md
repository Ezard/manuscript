# Manuscript

Frontend workshop / component library for Jetpack Compose

Heavily inspired by the excellent <a href="https://github.com/storybookjs/storybook">Storybook</a>

![Maven Central](https://img.shields.io/maven-central/v/io.ezard.manuscript/manuscript) 

## Getting Started

Simply add the dependency to your `build.gradle(.kts) file:

```kotlin
implementation("io.ezard.manuscript:manuscript:<latest-version>")
ksp("io.ezard.manuscript:ksp:<latest-version>")
```

## Documentation

API docs can be found here: https://ezard.github.io/manuscript/

## Usage

### Components

Components are the core focus of Manuscript

Set up a component by using the `Manuscript` composable

#### Controls

Controls allow you to update, in realtime, the data that your component is using

Set up a control by using the `control` function within a `Manuscript` context

Hint: use `val myControl by control(...)` instead of `val myControl = control(...)` for better ergonomics!

#### Actions

Actions allow you to see when certain interactions with your component occur

Set up an action by using the `action` function within a `Manuscript` context

Trigger the action by calling the `trigger()` function on the action

#### Variants

Variants allow you to group together closely-related components that share the same data; usually this is things like buttons of different colours, horizontal/vertical versions of a card, etc

Set up a variant by using the `Variant` function within a `Manuscript` context

#### Example

```kotlin
@Composable
fun ButtonManuscript() {
    Manuscript {
        val text by control("Text", "Click me!")
        val onClick = action(name = "onClick")

        Variant("Red") {
            Button(
                text = text,
                color = Color.Red,
                onClick = { onClick.trigger() },
            )
        }
        Variant("Green") {
            Button(
                text = text,
                color = Color.Green,
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