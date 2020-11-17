# aa2020
Проект для курса [Android Fundamentals](https://android-academy-global.github.io/) (Android Academy 2020)

[Related links](#related-links)
<br/>

## Homework #2: View & ViewGroups
- Макет movie_details
<br/>

<details>
  <summary>Click to see gif demo</summary>
  
  ![MovieDetails](/doc/aa2020_hw2.gif)
</details>

<details>
<summary>res/layout/activity_movie_details.xml</summary>
 
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="@color/background">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/title_image"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:src="@drawable/orig"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            android:adjustViewBounds="true"
            android:scaleType="fitCenter"
            />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/back"
            android:textColor="@color/white"
            android:textSize="12sp"
            android:alpha="0.5"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            android:layout_marginStart="18dp"
            android:layout_marginTop="56dp"
            android:gravity="center_vertical"
            app:drawableStartCompat="@drawable/ic_arrow_left" />

        <androidx.constraintlayout.widget.Guideline
            android:id="@+id/leftGuide"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintGuide_begin="16dp"
            android:orientation="vertical"
            />

        <androidx.constraintlayout.widget.Guideline
            android:id="@+id/rightGuide"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintGuide_end="16dp"
            android:orientation="vertical"
            />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/rating"
            style="@style/text_rating"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            app:layout_constraintBottom_toBottomOf="@id/title_image"
            android:layout_marginBottom="8dp"
            />

        <TextView
            android:id="@+id/title_text"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/avengers_end_game"
            style="@style/text_movie_title"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            app:layout_constraintEnd_toStartOf="@id/rightGuide"
            app:layout_constraintTop_toBottomOf="@id/title_image"
            />

        <TextView
            android:id="@+id/tag_text"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/action_adventure_fantasy"
            style="@style/text_movie_tag"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            app:layout_constraintEnd_toStartOf="@id/rightGuide"
            app:layout_constraintTop_toBottomOf="@id/title_text"
            />

        <ImageView
            android:id="@+id/star1_image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_star"
            app:layout_constraintTop_toBottomOf="@id/tag_text"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            android:layout_marginTop="8dp"
            app:tint="@color/red_star" />

        <ImageView
            android:id="@+id/star2_image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_star"
            app:layout_constraintStart_toEndOf="@id/star1_image"
            app:layout_constraintTop_toTopOf="@id/star1_image"
            app:tint="@color/red_star"/>

        <ImageView
            android:id="@+id/star3_image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_star"
            app:layout_constraintStart_toEndOf="@id/star2_image"
            app:layout_constraintTop_toTopOf="@id/star2_image"
            app:tint="@color/red_star"/>

        <ImageView
            android:id="@+id/star4_image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_star"
            app:layout_constraintStart_toEndOf="@id/star3_image"
            app:layout_constraintTop_toTopOf="@id/star3_image"
            app:tint="@color/red_star"/>

        <ImageView
            android:id="@+id/star5_image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_star"
            app:layout_constraintStart_toEndOf="@id/star4_image"
            app:layout_constraintTop_toTopOf="@id/star4_image"
            app:tint="@color/blank_star"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="0dp"
            android:text="@string/_125_reviews"
            style="@style/text_movie_reviews"
            android:gravity="center_vertical"
            android:layout_marginStart="8dp"
            app:layout_constraintStart_toEndOf="@id/star5_image"
            app:layout_constraintTop_toTopOf="@id/star5_image"
            app:layout_constraintBottom_toBottomOf="@id/star5_image" />

        <TextView
            android:id="@+id/storyline_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/storyline"
            style="@style/text_title_small"
            app:layout_constraintTop_toBottomOf="@id/star1_image"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            android:layout_marginTop="24dp"
            />

        <TextView
            android:id="@+id/storyline_text"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/after_the_devastating"
            style="@style/text_storyline"
            android:alpha="0.75"
            app:layout_constraintTop_toBottomOf="@id/storyline_title"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            app:layout_constraintEnd_toStartOf="@id/rightGuide"
            android:layout_marginTop="4dp"
            android:paddingTop="12dp"
            android:paddingBottom="8dp"
            />

        <TextView
            android:id="@+id/cast_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/cast"
            style="@style/text_title_small"
            app:layout_constraintTop_toBottomOf="@id/storyline_text"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            android:layout_marginTop="24dp"
            />

        <ImageView
            android:id="@+id/actor1_image"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:src="@drawable/actor1"
            app:layout_constraintTop_toBottomOf="@id/cast_title"
            app:layout_constraintStart_toStartOf="@id/leftGuide"
            app:layout_constraintEnd_toStartOf="@id/actor2_image"
            android:layout_marginTop="7dp"
            android:layout_marginEnd="4dp"
            android:adjustViewBounds="true"
            android:scaleType="fitCenter"
            />

        <TextView
            android:id="@+id/actor1_name"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/robert_downey_jr"
            style="@style/text_actor_name"
            app:layout_constraintTop_toBottomOf="@id/actor1_image"
            app:layout_constraintStart_toStartOf="@id/actor1_image"
            app:layout_constraintEnd_toEndOf="@id/actor1_image"
            />

        <ImageView
            android:id="@+id/actor2_image"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:src="@drawable/actor2"
            app:layout_constraintTop_toTopOf="@id/actor1_image"
            app:layout_constraintStart_toEndOf="@id/actor1_image"
            app:layout_constraintEnd_toStartOf="@id/actor3_image"
            android:layout_marginStart="4dp"
            android:layout_marginEnd="4dp"
            android:adjustViewBounds="true"
            android:scaleType="fitCenter"
            />

        <TextView
            android:id="@+id/actor2_name"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/chris_evans"
            style="@style/text_actor_name"
            app:layout_constraintTop_toBottomOf="@id/actor2_image"
            app:layout_constraintStart_toStartOf="@id/actor2_image"
            app:layout_constraintEnd_toEndOf="@id/actor2_image"
            />

        <ImageView
            android:id="@+id/actor3_image"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:src="@drawable/actor3"
            app:layout_constraintTop_toTopOf="@id/actor2_image"
            app:layout_constraintStart_toEndOf="@id/actor2_image"
            app:layout_constraintEnd_toStartOf="@id/actor4_image"
            android:layout_marginStart="4dp"
            android:layout_marginEnd="4dp"
            android:adjustViewBounds="true"
            android:scaleType="fitCenter"
            />

        <TextView
            android:id="@+id/actor3_name"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/mark_ruffalo"
            style="@style/text_actor_name"
            app:layout_constraintTop_toBottomOf="@id/actor3_image"
            app:layout_constraintStart_toStartOf="@id/actor3_image"
            app:layout_constraintEnd_toEndOf="@id/actor3_image"
            />

        <ImageView
            android:id="@+id/actor4_image"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:src="@drawable/actor4"
            app:layout_constraintTop_toTopOf="@id/actor3_image"
            app:layout_constraintStart_toEndOf="@id/actor3_image"
            app:layout_constraintEnd_toStartOf="@id/rightGuide"
            android:layout_marginStart="4dp"
            android:adjustViewBounds="true"
            android:scaleType="fitCenter"
            />

        <TextView
            android:id="@+id/actor4_name"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@string/chris_hemsworth"
            style="@style/text_actor_name"
            app:layout_constraintTop_toBottomOf="@id/actor4_image"
            app:layout_constraintStart_toStartOf="@id/actor4_image"
            app:layout_constraintEnd_toEndOf="@id/actor4_image"
            app:layout_constraintBottom_toTopOf="@id/barrierBottom"
            />

        <androidx.constraintlayout.widget.Barrier
            android:id="@+id/barrierBottom"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:barrierDirection="bottom"
            app:constraint_referenced_ids="actor1_name, actor2_name, actor3_name, actor4_name"
            />

        <Space
            android:layout_width="match_parent"
            android:layout_height="16dp"
            app:layout_constraintTop_toBottomOf="@id/barrierBottom"
            />

    </androidx.constraintlayout.widget.ConstraintLayout>

</ScrollView>
```
</details>

<details>
 <summary>res/values/strings.xml</summary>
 
 ```xml
<resources>
    <string name="app_name">My Application</string>
    <string name="back">Back</string>
    <string name="rating">13+</string>
    <string name="avengers_end_game">Avengers:
        End&#160;Game</string>
    <string name="action_adventure_fantasy">Action, Adventure, Fantasy</string>
    <string name="_125_reviews">125 Reviews</string>
    <string name="storyline">Storyline</string>
    <string name="after_the_devastating">After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe.</string>
    <string name="cast">Cast</string>
    <string name="robert_downey_jr">Robert Downey&#160;Jr.</string>
    <string name="chris_evans">Chris Evans</string>
    <string name="mark_ruffalo">Mark Ruffalo</string>
    <string name="chris_hemsworth">Chris Hemsworth</string>
</resources>
```
 </details>

<details>
 <summary>res/values/colors.xml</summary>
 
 ```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF6200EE</color>
    <color name="purple_700">#FF3700B3</color>
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>

    <color name="background">#191926</color>
    <color name="title_text">#ECECEC</color>
    <color name="tag_text">#FF3466</color>
    <color name="red_star">#FF3365</color>
    <color name="blank_star">#6D6D80</color>
    <color name="reviews_text">@color/blank_star</color>
    <color name="actor_name">#D8D8D8</color>
</resources>
```
</details>
 
<details>
 <summary>res/values/styles.xml</summary>
 
 ```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="text_rating" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/white</item>
        <item name="android:textSize">12sp</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="text_movie_title" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/title_text</item>
        <item name="android:textSize">40sp</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="text_title_small" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/title_text</item>
        <item name="android:textSize">14sp</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="text_movie_tag" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/tag_text</item>
        <item name="android:textSize">14sp</item>
    </style>
    <style name="text_movie_reviews" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/reviews_text</item>
        <item name="android:textSize">14sp</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="text_storyline" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/white</item>
        <item name="android:textSize">14sp</item>
    </style>
    <style name="text_actor_name" parent="TextAppearance.AppCompat">
        <item name="android:textColor">@color/actor_name</item>
        <item name="android:textSize">12sp</item>
        <item name="android:textStyle">bold</item>
    </style>
</resources>
```
</details>

<details>
 <summary>webp images</summary

<br/>

orig.webp

![orig](/app/src/main/res/drawable/orig.webp)

actor1.webp, actor2.webp, actor3.webp, actor4.webp

![Actor1](/app/src/main/res/drawable/actor1.webp) ![Actor2](/app/src/main/res/drawable/actor2.webp) ![Actor3](/app/src/main/res/drawable/actor3.webp) ![Actor4](/app/src/main/res/drawable/actor4.webp)


</details>

<br/>

## Предыдущие задания

<details>
<summary>Homework #1: Default Project Setup</summary>
 
- Создать пустой проект

[Линк на версию /aa2020/tree/1-homework](https://github.com/sergereinov/aa2020/tree/1-homework)

</details>
<br/>
 

## Related links
 - [habr](https://habr.com/ru/news/t/522972/)
 - [youtube playlist](https://www.youtube.com/playlist?list=PLjLCGE4bVpHCJvtGpEVl-4IYGHB1A8FCc)
 - Android Academy Msk: https://t.me/AndroidAcademyMsk
 
