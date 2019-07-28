# TimeTracker

### Time

It tooks me about 10 hours to finish the app. I didn't feel pressure to finish as quick as possible, so I took my time to make some designs, make a few tests and take it easy in general. At the end there is a tracking of the process.

### What I like about the project?
It looks nice, and it works fine. I enjoyed doing it. 

### What I don't like about the project?
With more time I would have tried to avoid some of the "duplication" code that I introduced

### What whould I have done diffetently if I would have had more time?
Add more tests, both UI and Unit.

### With what part am I satisfied?
I didn't do many tests at the end because I had no much time, but I'm pretty satisfied with the test of the first screen. Maybe it's not perfect, but it tests plenty of cases so it would be able to refactor some UI with confidence.

### What whould need improvements?

I keep it simple, but with more time I would add some threads to manage the request to the database in a background thread and not in the main thread. And with this would be probably needed some load screens.


### Process

#### Saturday

14:30 -> I start with the design. Following the specifications, I'm thinking about what should be the most friendly design. Tabs or bottom navigation bar come to my mind. It's also possible to add a button in the toolbar on the top to open the list of booked tracked times. I think I will go for the tab bar just below the toolbar. I think it's the most intuitive options.  

14:40 -> I want to do this good but quick. I will pick three nice colors but i don't gonna think about them to much. Blue, for example, will be good for the app. 

14:45 -> For the same reason, I will be a bit quick and don't think too much the wording of the app, at least not in this first version. Maybe, at the end, when everything is working fine, I find better titles for the sections 

14:54 -> Maybe the icon of an empty box to represent an empty list is not the best choice, I will keep it in the back of my mind. I like the icon, but it's not about what I like but what the users should understand

14:58 -> I just thought about a cool animation that I could include to start the time tracker. 

15:04 -> Big buttons, big buttons everywhere

15:09 -> I think I will put all the controls related with the end of the tracking in a dialog. I kind of imagine it already and I see it pretty clear and friendly. 

15:10 -> There is a lack of icons in the app. I don't know if I should solve this or limited to text. 

15:30 -> Is it allowed to open a dialog when pressing a button on a dialog? It's possible. And probably I'm not the best designer even in this room

15:36 -> Now I'm seeing that maybe the tabs strategy was not the best because one of the screens has 2 specific buttons for that view (add tracked time and search for a tracked time description). Damn you Ayo from the past!

15:40 -> But I like the idea of use a grid in the list of booked times. 

15:44 -> Ok. I will change a bit the design to make the list of booked times another activity. So, remove the tabs and I add a button in the toolbar. 

15:51 -> Great. The design is ready. It took me one and a half hour, but finally I can start to plan some tests. 

16:05 -> After plan some tests I already have a basic idea of what I want to do, how I will do it and what I'm gonna use to do it. Basic MVP as architecture, Objectbox as local database (I like it and I wanted to use it in a project asap), but still don't know if use RxJava or not.

16:12 -> So, all the dependencies that I need to start are already there. Let's start this thing

16:32 -> First UI test, fails. Perfect. I include the minimal content to make it work, run it and...

16:34 -> Green. There is no need to refactor so I continue

19:39 -> Perfect. First screen finished. 

22:05 -> Finish for today. The app si almost finished. I only have to implement the search function, the ability to introduce custom time and fix small design issues

#### Sunday

08:38 -> A new day start. And with it, the conclusion of this task. So, let's hit a bit this search functionality.

09:34 -> Perfect, the search functionality is working fine now. I also did some fixed in UI elements. The last thing is now do number introduction to add new time tracked. And I don't know how to do it, because my first idea won't work. I have to think a bout it a bit

09:41 -> I think I got an idea. Probably it's not the nicer solution, but it will work

10:50 -> Perfect. The app it now totally working. There are some small UI changes that could be applied, but the app is working fine so, I will count it as  finished now. 
