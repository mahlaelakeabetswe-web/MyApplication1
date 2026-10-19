# Life Hack or Urban Myth?

**Student name:** Keabetswe Mahlaela  
**Student number:** ST10499250  
**Module:** IMAD Assignment 2

## App purpose
Life Hack or Urban Myth? is a native Android quiz application developed in Kotlin. It helps users think critically about popular life hacks and internet rumours. Players decide whether each statement is a useful hack or a myth and receive feedback and an explanation.

## Main features
- Welcome screen explaining the quiz.
- Start Quiz button.
- Seven true/false style questions.
- Immediate feedback after each answer.
- Score screen with percentage and personalised feedback.
- Review Answers screen showing the correct answers and explanations.
- Play Again option.

## Design considerations
The app uses a simple layout, clear headings, readable text, and large buttons that are easy to tap. A light background and dark blue headings help create contrast. The quiz is presented one question at a time to reduce clutter and keep the user focused.

## Application logic
The questions are stored in a Kotlin list using a data class. The app tracks the current question, the score, and the user's answers. When the user selects an answer, the app checks it against the correct answer, updates the score when appropriate, and displays feedback. After the last question, the score screen calculates a percentage and uses a `when` statement to choose feedback. The review screen displays all questions, correct answers, the user's answers, and explanations.

## Testing
Manually test the following:
1. Start Quiz opens the first question.
2. Hack and Myth buttons each give feedback.
3. A correct answer increases the score by one only.
4. Next Question displays the next statement.
5. The final question leads to the score screen.
6. Score and percentage match the answers selected.
7. Review Answers displays correct answers and explanations.
8. Play Again resets the score and answers.
9. Rotate or reopen the app and check for crashes if your test device supports these actions.

## GitHub and GitHub Actions
Create a GitHub repository and push the project files. The workflow in `.github/workflows/android-build.yml` runs a Gradle build when code is pushed or a pull request is opened. Check the Actions tab on GitHub to see whether the build passes.

## Video demonstration
Record a short screen recording with a voice-over. Show the welcome screen, answer several questions, explain the feedback, show the score, open Review Answers, and use Play Again. Add the unlisted YouTube video link here before submission:

**Video link:** Add your unlisted video URL here.

## Build requirements
Open the project in Android Studio. Allow Gradle to sync, then run the app on an emulator or Android device. If Android Studio asks to install a missing SDK, install the SDK version requested by the project.
