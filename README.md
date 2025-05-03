# shopping_list_android_app


# Detekt

In case if you face with issue :
permission denied: ./gradlew

Run command

```
 chmod +x gradlew
```

To check lint issues

```
./gradlew detekt
```
Or use

```
bash gradlew detekt
```

In folder `build/reports/detekt/detekt.html`

Report details will be located

In gradle tasks `detekt` is located under `verification` task