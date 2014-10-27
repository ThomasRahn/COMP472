# COMP472

Thomas Rahn (6286852), Alan Ly (6293484)

"We certify that this submission is the original work of members of the group and meets the
Faculty's Expectations of Originality"

## Deliverable #1

### How to run:
Open up the project in your favourite IDE-flavour, then hit the green play button to run `Main.java`.

### Process:

This program will read in all 1000 Spam files and 1000 ham files one-by-one. Then, it will filter out all punctuation and special characters (as defined below), words with numerical characters in them, as well as any word less than a length of 3. It will then extract the valid word and add them to a Map to keep track of the number of occurances, then create a List of "words" with that information.

Once the list of words has been created it will perform the calculations, apply the appropriate smoothing and store it in a `model.txt` file with the format specified by the project specifications.

### Our assumptions:

We removed all punctuation and special characters which are:

    . , + = ( ) [ ] < > " _ ! @ # $ % ^ & * / \ { } | : ; ` ~ -
		
Invalid words:

- Any word with a numerical character.
- Any word with 3 or less characters.
