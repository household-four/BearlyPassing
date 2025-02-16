NOTES:

TO-DO:
    Need to create wireframes for the different pages and views
    Create a repository to store data
    Add dummy data to repository
    Create services to handle business logic; e.g. teacherservice to assign a game to a student, game service to create or start game, etc
    Create controllers and endpoints



Architecture:
    What GRASP paradigms?
        Particularly for the game portion.
    Likely MVC since this is a three-layer design.
        Layers: Presentation, Business Layer, Database

Unimplemented Classes:
    Course
    Question Set // class or attribute associated with user?
    Question
    Media (photo, audio, video) // attribute or class?
    Feedback // attribute on question or quiz result?

Classes/Methods Extracted from Use Cases:
    Student wants:
        multiple game modes
            \GET problem set selection -> Displays all problem sets available to the student
            \GET select set -> Displays game options (e.g., game, flash cards, quiz)
            \GET selection -> Displays game || flash cards || quiz
            plays game...
            ?? where do we store game/quiz results? ??
            
            extensions: 
                unable to generate quiz, return to selection screen with error message
                ?? wants multiple game modes, gets quiz with mixed question types ??

        offline practice
        SRS (or another system) for tracking problem questions
            can also star questions
        get feedback from teachers on responses/quizzes 
        ranking/leaderboard system

    Teacher wants:
        share question sets with students (based on course or specific students)
            ?? is this different than assigning questions to students ??    

        import (quiz) questions from Canvas SPECIAL OPTION FOR TEACHERS
            \GET problem creation section
            \GET import quiz option -> system file window for navigation and input
                If easier, just have them enter a path to the file...
                Type has to be QTI ZIP or unzipped into XML.
            parse file for input questions and display to user
                If bad input, notify to try again.
            \GET study set owner view:
                user can edit, delete, or (manually) add questions
                    each question has a pencil and a transh can icon
                    below the list is a + for adding more questions
            Button at the bottom. Click add to new study set or existing sets
            ?? how do we deal with the many-to-many relationship here ??
                maybe just loop until they are done 
                - i.e., they can keep adding questions to one set at a time 
                it feels like it'd be best for the user if two columns and can like drag and drop
                (similar to matching tests where you draw lines between the two columns)
            system updates those problem sets with the new questions
            ?? does the system also store questions in general or just sets ??
                feels like it should store questions in general and then just check attribute for set associations
            save changes
            \GET problem creation section

        track question set difficulty to see what questions students struggle with
        see scores of my students
        assign questions/games to students
        see student view
        give feedback on practice quiz

        share questions

    Admin (School) wants
        see platform usage/student engagement.
        monitor question contents according to school guidelines.

    User wants:
        // system autosaves by default
        
        whenever viewing a question set (that you control), 
            can see pencil for editing,
            trash for deleting, 
            and plus icons below list for adding.

        upload photos
        upload audio
        import and export questions/sets (to a locally editable format)
            !! relates to teacher's import quiz option !!

        create timed quizzes and challenges // just an option in each game mode, a checkbox before playing
        
        login (with default acounts for now)
        Don't do yet: (logout, sign up, update profile.)

    System
        display problem format
        retrieve feedback data
        fetch question data