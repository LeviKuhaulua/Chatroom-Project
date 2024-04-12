# Setup for JavaFX with Scene Builder. 

<br> 

## Need

1. [JavaFX SDK](https://gluonhq.com/products/javafx/) - version `21.0.2`
2. [JavaFX SceneBuilder](https://gluonhq.com/products/scene-builder/) - version `21.0.0`

**Steps**: 
- Download the JavaFX SDK and SceneBuilder. 
- Extract the contents of JavaFX SDK to a directory of your choosing. REMEMBER THE FILE PATH - IT'LL BE USED LATER. 
- SceneBuilder will be explained later. 


<br> 

## Initial Setup
1. Make sure to have Git installed. You can do `git -v` to verify that. 
2. Navigate to the directory that you want to hold the project 
3. Run the following in your Terminal: `git clone https://github.com/LeviKuhaulua/Chatroom-Project.git`
4. Create and switch to your branch with this command `git checkout -b <replace this with name that you want>` 

## Git Commands 

To add your changes to your branch, you have to use one of these commands 

- `git add .` - to add EVERY file 
- `git add filename1.extension filename2.extension` - to add specific file(s) 

Then commit these changes and put a note on what you did using this command: 

- `git commit -m <message on what you did>` 

Finally changes to YOUR branch using: 

- `git push -u origin <BRANCH NAME>` 

<br> 

To get updates on the code for any branch you can use: 

- `git pull origin main` 
- `git pull origin <BRANCH NAME>`


## Running the Program
1. In the Project Folder, go to Run > Add Configurations 
2. In the `launch.json` folder, add the following to the code block below 
```json
{
    [
        "type": "java", 
        "name": "<NAME OF FILE W/ JAVAFX CODE>", 
        "request": "launch", 
        "mainClass": "<NAME OF FILE W/ JAVAFX CODE>", 
        /*
            Below is what's being added to the code block. Note that you may need to use '\' for the path to javafx-sdk-21.0.2/lib. 
            For Windows you may need to use the following instead: 
            "vmArgs": "--module-path \"Path/to/jfx-sdk-21.0.2/lib\" --add-modules javafx.controls,javafx.fxml"
        */ 
        "vmArgs": "--module-path /path/to/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml", 
    ]
}
```
***NOTE***: For any java file that contains JavaFX, you will need to add another code block with the same parameters as the one above to get it to run using Code Runner extension. 

## Using SceneBuilder
1. You can open up SceneBuilder app > then do an empty file to use the GUI Editor. 
2. Drag n' drop elements into the Editor and modify their values. 
3. Controller section > Rename Controller Class to something meaningful (i.e client1Controller) 
4. Save the `.fxml` file into the `src` folder of the project. 
5. Go to View > Show Sample Controller Skeleton. Then copy the code inside. 
6. Create a `.java` file in the src folder with the same name as the one in Step 3
7. Paste code inside. 

**NOTE** Main thing to remember is that for each java file with JavaFX, you will need two more files, the Controller file which is another java file, and the fxml file. 

Look at YouTube for instructions on designing / making GUI. 

## Issues You May Encounter

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

