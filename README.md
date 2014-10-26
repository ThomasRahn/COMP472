# COMP472

Thomas Rahn (6286852)
Alan Ly (6293484)

## Deliverable #1

### Process:

This program will read in all 1000 Spam files and 1000 ham files one by one and extra the valid words from them. It will filter out all punctuation/special characters, words with a numerical character in them, as well as any word with less then 3 characters. It will then add the words to a HashMap and create a List of "Words" with that information.

Once the list of words have been created it will perform the calculations and appropriate smoothing and store it in model.txt with the format specified by the project specifications.

### Our assumptions:

We removed all punctuation and special characters which are:
    . , + = ( ) [ ] < > " _ ! @ # $ % ^ & * / \ { } | : ; ` ~
		
Invalid words:
- Any word with a numerical character.
- Any word with 3 or less characters.