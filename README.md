# jid3lib
Java library implementing ID3 tags for MP3 files

This library reads song information, such as song title, artist, and album, from an MP3 file. It supports ID3v1, ID3v1.1, Lyrics3v1, Lyrics3v2, ID3v2.2, ID3v2.3, and ID3v2.4 tags. MP3 Frame Headers can also be read. There is a FilenameTag, a ID3v2.4 tag that is intelligently derived from the file name. It contains tag synchronization utilities, multiple save options, and easy tag conversion methods.


## MP3 Tag java library Quick Start


True to the title, this is quick. There are many more read/write/edit options if you go through the JavaDocs or through the code.

**Introduction to tags**

There are three types of tags found in an MP3 file found in this order:
- ID3v2
- *MP3 Data*
- Lyrics3
- ID3v1

In addition, there are different versions for each tag:
1. ID3v2
  * ID3v2.2
  * ID3v2.3
  * ID3v2.4
2. Lyrics3
  * Lyrics3v1
  * Lyrics3v2
3. ID3v1
  * ID3v1.0
  * ID3v1.1

**Compiling:**

This is a simple Maven project. To build, just use
`mvn clean package`

**Reading:**
```
File sourceFile;
MP3File mp3file = new MP3File(sourceFile);
if (mp3file.hasID3V2Tag()) {
    AbstractID3v2 tag = mp3file.getID3v2Tag();
    String albumTitle = tag.getAlbumTitle();
}
```
You can also read specific tags:
```
ID3v1_1 tag = new ID3v1_1(sourceFile);
ID3v1 tag = new ID3v1(sourceFile);
ID3v2_4 tag = new ID3v2_4(sourceFile);
ID3v2_3 tag = new ID3v2_3(sourceFile);
ID3v2_2 tag = new ID3v2_2(sourceFile);
Lyrics3v2 tag = new Lyrics3v2(sourceFile);
Lyrics3v1 tag = new Lyrics3v1(sourceFile);
```

**Creating:**

```
MP3File mp3file = new MP3File("mysong.mp3");
TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);

// setup id3v1
ID3v1_1 id3v1 = new ID3v1_1();
id3v1.setAlbumTitle("Album Title");
mp3file.setID3v1Tag(id3v1);
mp3file.save();

// setup id3v2
ID3v2_3 id3v2 = new ID3v2_3();
id3v2.setAlbumTitle("Album Title");
mp3file.setID3v2Tag(id3v2);
mp3file.save();

// setup lyrics3v2
Lyrics3v2 lyrics3v2 = new Lyrics3v2();
lyrics3v2.setAlbumTitle("Album Title");
mp3file.setLyrics3Tag(lyrics3v2);
mp3file.save();

// setup filename tag
FilenameTag filenameTag = new FilenameTag();
filenameTag.setAlbumTitle("Album Title");
mp3file.setFilenameTag(filenameTag);
mp3file.save();
```

**Things to note:**
The default save mode is "write but do not delete." This means each field in the object will be saved, but existing fields in the file on disk will not be deleted. The other two are "only append" or "delete and write from scratch."

**Editing Part 1:**

There are convience methods defined in AbstractMP3Tag to edit common data fields. Not all tags have all fields listed here.
```
public abstract String getSongTitle();
public abstract String getLeadArtist();
public abstract String getAlbumTitle();
public abstract String getYearReleased();
public abstract String getSongComment();
public abstract String getSongGenre();
public abstract String getTrackNumberOnAlbum();
public abstract String getSongLyric();
public abstract String getAuthorComposer();
public abstract void setSongTitle(String songTitle);
public abstract void setLeadArtist(String leadArtist);
public abstract void setAlbumTitle(String albumTitle);
public abstract void setYearReleased(String yearReleased);
public abstract void setSongComment(String songComment);
public abstract void setSongGenre(String songGenre);
public abstract void setTrackNumberOnAlbum(String trackNumberOnAlbum);
public abstract void setSongLyric(String songLyrics);
public abstract void setAuthorComposer(String authorComposer);
```

**Editing Part 2:**

If the field you want is not listed above, you can use these methods.
```
id3v1 = mp3file.getID3v1Tag();
id3v2 = mp3file.getID3v2Tag();
lyrics3 = mp3file.getLyrics3Tag();
```

ID3v1 tags have fixed fields and use accessor methods to change it's properties.

ID3v2 tags have multiple frames. Use this to set the title of the tag.
```
// setup id3v2
frameBody frameBody = new FrameBodyTALB((byte) 0, "albumTitle");
AbstractID3v2Frame frame = new ID3v2_4Frame(frameBody);
id3v2.setFrame(frame);
```    
Lyrics3 tags have multiple fields. Use this to set the title of the tag.
```
// setup lyrics3v2
AbstractLyrics3v2FieldBody fieldBody = new FieldBodyEAL("albumTitle");
Lyrics3v2Field field = new Lyrics3v2Field(fieldBody);
lyrics3.setField(field);
```
    
**Writing:**
```    
mp3file.save();
```
You can also save each individual tag through each tags' save() method.

**Reference:**
Here is a mapping of the commonly used fields

|Field Name |ID3v1 |ID3v1.1 |ID3v2.2 |ID3v2.3 |ID3v2.4 |Lyrics3v1 |Lyrics3v2|
|-----------|------|--------|--------|--------|--------|----------|---------|
|Song Title|songTitle|songTitle|TT2|TIT2|TIT2||ETT|
|Lead Artist|leadArtist|leadArtist|TP1|TPE1|TPE1||EAR|
|Album Title|albumTitle|albumTitle|TAL|TALB|TALB||EAL|
|Year Released|yearReleased|yearReleased|TYE|TYER|TDRC|||
|Comment|songComment|songComment|COM|COMM|COMM||INF||
|Song Genre|songGenre|songGenre|TCO|TCON|TCON|||
|Track number on album|trackNumberOnAlbum|trackNumberOnAlbum|TRK|TRCK|TRCK|||
|Lyrics|||SYL or ULT|SYLT or USLT|SYLT or USLT|lyric|LYR|
|Author / Composer|||TCM|TCOM|TCOM||AUT|

## Further Documentation
(Copied from www.id3.org)

- [The short history of tagging](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/history.html)
- [ID3v1 made easy](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/ID3%20made%20easy.htm)
- [ID3v2 made easy](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/easy.html) 
- [Lyrics3 made easy](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/Lyrics3%20v2.00.htm) 


- [ID3 v 2.2](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/ID3%20v2.2.0%20Commented.htm)
- [ID3 v 2.3](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/ID3%20v2.3.0.htm)
- [ID3 v 2.4 Structure](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/ID3%20v2.4.0%20Structure.htm)
- [ID3 v 2.4 Frames](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/ID3%20v2.4.0%20Frames.htm)
- [ID3 v 2.4 Changes](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/ID3%20v2.4.0%20Structure.htm)
- [Lyrics3 v2.00](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/Lyrics3%20v2.00.htm)


- [The private life of MP3 frames](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/The%20private%20life%20of%20MP3%20frames.htm)
- [MP3 Audio Frame Heaer](http://htmlpreview.github.com/?https://github.com/ericfarng/jid3lib/blob/master/doc/MPEG%20Audio%20Frame%20Header.htm)




