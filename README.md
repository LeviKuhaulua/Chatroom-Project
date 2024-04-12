# Setup for JavaFX with Scene Builder. 

<br> 

**Need**

1. [JavaFX SDK](https://gluonhq.com/products/javafx/) - version `21.0.2`
2. [JavaFX SceneBuilder](https://gluonhq.com/products/scene-builder/) - version `21.0.0`

<br> 

**Video Guide when steps are confusing** 

Link: [https://youtu.be/YtXtwar4v9U?si=tZhCyaWOXs0CDh2x]()

<br> 

**Steps**

1. Download the JavaFX SKD and extract the contents to any location and download the JavaFX SceneBuilder. 
2. In Visual Studio Code, open the Command Palette, then do Java: Create Java Project > No build tools. 
    - Windows Shortcut (Command Palette): <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>P</kbd> 
    - Mac Shortcut (Command Palette): <kbd>&#8984;</kbd> + <kbd>&#8679;</kbd> + <kbd>P</kbd> 
3. Go to the javafx sdk folder > `lib` folder and copy all the `.jar` files inside. 
4. Paste the files into the `lib` folder of the Java project you created. In the Java Projects tab, you should see the javafx libraries in the `Referenced Libraries` section. 
5. Click on Run > Add Configurations...
6. In the `launch.json` file, add this line of code: 
    - Windows: `"vmArgs": "--module-path \"Path/to/jfx-sdk-21.0.2/lib\" --add-modules javafx.controls,javafx.fxml"`

    <br> 

    - Mac: `"vmArgs": "--module-path /path/to/javafx-sdk-22/lib --add-modules javafx.controls,javafx.fxml"`
7. Open up SceneBuilder then build a test scene. 
8. In the Controller section (left-hand side), name the Controller class to whatever you like. 
9. Save the file and put the file into the `src` folder of the Java Project
10. Click on View > Show Sample Controller Skeleton. Then copy the code. 
11. Create a new Java file with the same name as the one in Step 8, then paste that code in.
12. Run the file, you should see the JavaFX GUI pop up. 


**Issues You May Encounter**

- You may not be able to run the files, getting an error message that you are missing JavaFX components, if that's the case, please ensure that you have ONLY the Java Project Folder open. What I mean is if your project folder is named `Chatroom` and it's in a folder called `Container`, you should open the `Chatroom` folder and not `Container`. 

- For JavaFX 21, you need at least JDK 17 to run. If you need to, create a `settings.json` file in the `.vscode` folder, then proceed to add the following code block. 

```json
{
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-21", 
            "path": "path/to/jdk-21", // You may need to use '\' for the path 
            "default": true, // if true then use that jdk whenever you open up project. Make sure only ONE is set to true or leave out default for the ones you are not going to use.  

        }, 

        ... add more here following same format
    ]
}
```

