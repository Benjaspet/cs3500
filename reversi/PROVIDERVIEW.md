## Provider View

### Features we were able to get working:
- [x] A successful implementation of their provided strategy, MaxCaptureStrategy. We
were adapt their axial coordinate system to our own coordinate system in order to get
their strategy to work.
- [x] A successful implementation and adaptation of their view. We created a ViewAdapter
to adapt their view to our own view.

### To see these provider features in action:

Running provider strategy:

```shell
java -jar 7 human providerai
```

Running the provider's view:

Simply create a new ViewFactory object and pass in the model, player, and boolean
value of true to indicate that the view is the provider's view. Then call the build
method to return the view.

```java
GraphicsView viewPlayer2 = new ViewFactory(model, player2, true).build();
                                                             |
                                                             v
// Assert that you'd like to use the provider's view, and not our view.
```
Then, run the program with any options you'd like (reference our README.md for more information).
```shell
java -jar 7 human human
```