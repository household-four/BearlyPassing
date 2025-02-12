NOTES:

TO-DO:
    Need to create wireframes for the different pages and views
    STOPPED AT Use Cases - Fully Dressed. Continue reading documentation.


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
        offline practice
        SRS (or another system) for tracking problem questions
            can also star questions
        get feedback from teachers on responses/quizzes 
        ranking/leaderboard system

    Teacher wants:
        share question sets with students (based on course or specific students)
        import (quiz) questions from Canvas

        track question set difficulty to see what questions students struggle with
        see scores of my students    
        assign questions/games to students
        see student view

        share questions

    Admin (School) wants
        see platform usage/student engagement.
        monitor question contents according to school guidelines.

    User wants:
        create questions
        modify questions
        upload photos
        upload audio
        import and export questions/sets (to a locally editable format)
        create timed quizzes and challenges
        
        login, logout, sign up, update profile.

    System
        display problem format
        retrieve feedback data
        fetch question data