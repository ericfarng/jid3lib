/*
 * MP3 Tag library. It includes an implementation of the ID3 tags and Lyrics3
 * tags as they are defined at www.id3.org
 *
 * Copyright (C) Eric Farng 2004
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * MP3 Tag library. It includes an implementation of the ID3 tags and Lyrics3
 * tags as they are defined at www.id3.org
 *
 * Copyright (C) Eric Farng 2003
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.farng.mp3;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.farng.mp3.id3.AbstractMP3Tag;
import org.farng.mp3.object.AbstractMP3ObjectTest;
import org.farng.mp3.object.ObjectBooleanByteTest;
import org.farng.mp3.object.ObjectBooleanStringTest;
import org.farng.mp3.object.ObjectGroupRepeatedTest;
import org.farng.mp3.object.ObjectID3v2LyricLineTest;
import org.farng.mp3.object.ObjectLyrics3ImageTest;
import org.farng.mp3.object.ObjectLyrics3LineTest;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <p/>
 * Title: </p>
 * <p/>
 * <p/>
 * Description: </p>
 * <p/>
 * <p/>
 * Copyright: Copyright (c) 2003 </p>
 * <p/>
 * <p/>
 * Company: </p>
 *
 * @author $author$
 * @version 1.0
 */
public class AllTestCase extends TestCase {

    /**
     *
     */
    private static HashMap testClassMap = new HashMap();
    /**
     *
     */
    private static int missingClassCount = 0;

    /**
     * Creates a new AllTestCase object.
     */
    public AllTestCase(String testCaseName) {
        super(testCaseName);
    }

    /**
     * @throws ClassNotFoundException
     */
    public static void checkAllTestClassesExist() throws ClassNotFoundException {
        String missingClassString = "";
        missingClassString += checkTestClassesExist("org.farng.mp3.object");
        missingClassString += checkTestClassesExist("org.farng.mp3.id3");
        missingClassString += checkTestClassesExist("org.farng.mp3.filename");
        missingClassString += checkTestClassesExist("org.farng.mp3.lyrics3");
        missingClassString += checkTestClassesExist("org.farng.mp3");
        if (missingClassCount > 0) {
            fail(missingClassString + "and " + (missingClassCount - 5) + " more classes");
        }
    }

    /**
     *
     */
    public static void checkAllTestMethodsExist() {
        String message = "\n";
        Iterator iterator = testClassMap.keySet().iterator();
        Class testedClass;
        while (iterator.hasNext()) {
            testedClass = (Class) iterator.next();
            message += checkTestMethodsExist(testedClass, (Class) testClassMap.get(testedClass));
        }
        if (message.length() > 0) {
            fail(message);
        }
    }

    /**
     * @param packageName
     *
     * @throws ClassNotFoundException
     */
    public static String checkTestClassesExist(String packageName) throws ClassNotFoundException {
        String missingClasses = "";
        File sourceDir = new File("src/" + packageName.replaceAll("\\.", "/"));
        File[] sourceArray;
        sourceArray = sourceDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        File testDir = new File("test/" + packageName.replaceAll("\\.", "/"));
        File[] testArray;
        testArray = testDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("Test.java");
            }
        });
        String className;
        String testClassName;
        Class testerClass;
        Class testedClass;
        int index;
        for (int i = 0; i < sourceArray.length; i++) {
            className = sourceArray[i].getName();
            className = className.substring(0, className.length() - 5);
            testedClass = Class.forName(packageName + "." + className);
            if ((Modifier.isAbstract(testedClass.getModifiers()) == false) &&
                (Modifier.isInterface(testedClass.getModifiers()) == false)) {
                index = getTestArrayIndex(className, testArray);
                if (index < 0) {
                    if (missingClassCount <= 5) {
                        missingClasses += ("No test class found for " + packageName + "." + testedClass + "\n");
                    }
                    missingClassCount++;
                } else {
                    testClassName = testArray[index].getName();
                    testClassName = testClassName.substring(0, testClassName.length() - 5);
                    testerClass = Class.forName(packageName + "." + testClassName);
                    testClassMap.put(testedClass, testerClass);
                }
            }
        }
        return missingClasses;
    }

    public static String checkTestMethodsExist(Class baseClass, Class testClass) {
        Method[] methods = baseClass.getDeclaredMethods();
        String methodName;
        Method testMethod;
        String missingMethods = "";
        for (int i = 0; i < methods.length; i++) {
            if (Modifier.isPublic(methods[i].getModifiers()) &&
                (Modifier.isAbstract(methods[i].getModifiers()) == false)) {
                methodName = methods[i].getName();
                methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
                try {
                    testMethod = testClass.getDeclaredMethod("test" + methodName, null);
                    if (testMethod == null) {
                        throw new NoSuchMethodException("test" + methodName);
                    }
                } catch (NoSuchMethodException ex) {
                    missingMethods += ("No test method found for " +
                                       baseClass.getName() +
                                       "." +
                                       methods[i].getName() +
                                       " within " +
                                       testClass.getName() +
                                       "\n");
                }
            }
        }
        return missingMethods;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();

//        suite.addTest(new AllTestCase("checkAllTestClassesExist"));
//        suite.addTest(new AllTestCase("checkAllTestMethodsExist"));
        suite.addTest(TagUtilityTest.suite());
        suite.addTest(MP3FileTest.suite());
        suite.addTest(MiscellaneousTest.suite());
        suite.addTest(AbstractMP3Tag.suite());
        suite.addTest(AbstractMP3ObjectTest.suite());
        suite.addTest(ObjectBooleanByteTest.suite());
        suite.addTest(ObjectBooleanStringTest.suite());
        suite.addTest(ObjectGroupRepeatedTest.suite());
        suite.addTest(ObjectID3v2LyricLineTest.suite());
        suite.addTest(ObjectLyrics3ImageTest.suite());
        suite.addTest(ObjectLyrics3LineTest.suite());
        return suite;
    }

    private static int getTestArrayIndex(String className, File[] testArray) {
        if (testArray == null) {
            return -1;
        }
        for (int i = 0; i < testArray.length; i++) {
            if (testArray[i].getName().startsWith(className)) {
                return i;
            }
        }
        return -1;
    }
}
