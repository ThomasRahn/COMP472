# COMP472

Thomas Rahn (6286852), Alan Ly (6293484)

"We certify that this submission is the original work of members of the group and meets the
Faculty's Expectations of Originality"

## Deliverable #2

### How to run:
Open up the project in your favourite IDE-flavour, then hit the green play button to run `Main.java`.

### Process:

This program will read in all 1000 Spam files and 1000 ham files one-by-one. Then, it will filter out all punctuation and special characters (as defined below), words with numerical characters in them, as well as any word less than a length of 3. It will then extract the valid word and add them to a Map to keep track of the number of occurances, then create a List of "words" with that information.

Once all of the words have been stored in the list, we start to classify 400 Spam files, and 400 Ham files. We read each file one-by-one, and perform the same operations as the previous 2000 files. (Filtering words etc...) For each word in the files we calculate the score and with this score we will be able to classify whether the file is Spam or Ham. With this information we then create two txt files. 

The first file "Results.txt" contains:

	1) Line counter
	2) File name
	3) Our classification (Spam or Ham)
	4) The Ham score
	5) The Spam score
	
The second file "Analysis.txt" contains:

	1) Line counter
	2) File name
	3) Our classification
	4) The actual classification
	5) True or false if we were correct or not.

At the end of "Analysis.txt" we have the number of correct classifications, our classification accuracy and a confusion matrix.

### Our assumptions:

We removed all punctuation and special characters which are:

    . , + = ( ) [ ] < > " _ ! @ # $ % ^ & * / \ { } | : ; ` ~ -
		
Invalid words:

- Any word with a numerical character.

We have removed this rule for deliverable #2 to incease the accuracy of our classifiy.

- Any word with 3 or less characters.
