
# Pre-work - MyToDoApp

**MyToDoApp** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Khang Vu**

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] In DialogFragment: Dismiss keyboard after users finish typing
* [x] Disable "Save" button if EditText description is blank
* [x] Add App icon

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

_With virtual keyboard_

<img src='http://i.imgur.com/ZAuKSmI.gif' title='With virtual keyboard' width='' alt='Video Walkthrough' />

_Without virtual keyboard_

<img src='http://i.imgur.com/2PjhjQ0.gif' title='Without virtual keyboard' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.
- Sometimes the app runs abnormally! For instance, you cannot disable Done Button when EditText .isEmpty() in OnCreateView() or OnViewCreated(). The lines of code simply do not run in the if/else statement!!! Android bugs I believe.
```Java
// DialogFragment: OnCreateView() or OnViewCreated()
if (editText.toString().isEmpty()) {
            buttonSave.setEnabled(false);
            buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        } else {
            buttonSave.setEnabled(true);
            buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
        }
} // Do not work. No lines of code in if statement or else stament run!
```
- Poor debug framework! While debugging, the app may crash unexpectedly although it still runs normally without debugging!
- Dismissing and showing keyboard are so complicated. Showing keyboard also leads to UI bug and so I disable it!

## License

    Copyright 2015 Minh-Khang Vu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
