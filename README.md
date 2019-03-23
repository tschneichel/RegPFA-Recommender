# RegPFA-Recommender

How to use:

1. Run EmptyDataFile.bat

2. Move .tsml-files into data folder

3. Run Reader

4. Edit config.txt in data folder

5. Run Recommender

1)
When first downloading this program, it is recommended to run "EmptyDataFile.bat" first. 
You will be asked whether you want to delete the contents of your datafiles. 
Type "y" and hit enter twice to continue. 

2)
If you've already created .tsml-files, you can now move them into the data folder. 
If you haven't, use RegPFA to mine transition systems according to https://em.uni-muenster.de/wiki/Mining_with_RegPFA_algorithm
Afterwards, you can export them to your disk as a .tsml-file while viewing them in the ProM-workspace. 

3)
Next, you may run "Reader.bat" in order to construct a database from all .tsml-files in the data folder. 
They will be moved to the archive folder once the Reader finishes running, but you are free to delete them from there if you want. 
Whenever you wish to add additional automata to the database, convert them to the .tsml-format, move them to the data folder and run the Reader. 
Whenever you wish to delete your database, run EmptyDataFile.bat.


4)
In order to run the recommender, you need to edit the config.txt file in the data folder, specifically the second, fourth and sixth line in it. 
The second line must be a natural number, indicating the maximum amount of recommendations you want to receive.  
The fourth line must also be a natural number, indicating how little smaller subsequences are supposed to be weighed. 
A weightFactor of zero value implies no additional weighing will be done. 
The higher the number, the lower they are weighed. 
The sixth line must consist of a sequence of already transpired events enclosed by brackets and separated by commas. 
For example, it may look like this:

Number of Recommendations:

1

Weight Factor for Subsequences:

2

Previous Transition Sequence:

[Harry Truman, Doris Day, Red China, Johnnie Ray, South Pacific, Walter Winchell]


After you're all set up, you may run "Recommender.bat" which will first show you the sequence of events detailed in the config.txt file and then list as many recommendations as you specified. 
For the example above, it may look like this:

[Harry Truman, Doris Day, Red China, Johnnie Ray, South Pacific, Walter Winchell]  

Recommend Joe DiMaggio with probability 1.0

RegPFA_Recommender finished. Press enter to continue.
