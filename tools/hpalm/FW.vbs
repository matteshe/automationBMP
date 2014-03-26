'TEST_JAR = "C:\temp\dt\bmp.tests-1.0-SNAPSHOT-tests.jar"
TEST_JAR = "C:\Users\Daniel\workspace_telekom\de.telekom.testing\de.telekom.bmp.tests\target\bmp.tests-1.0-SNAPSHOT-tests.jar"

OUTPUT_DIR = "C:\temp\dt\out"
OUTPUT_FILE = "C:\temp\dt\result.zip"

Function DeleteFolder(strFolderPath)
	Dim objFSO, objFolder
	Set objFSO = CreateObject ("Scripting.FileSystemObject")
	If objFSO.FolderExists(strFolderPath) Then
		objFSO.DeleteFolder strFolderPath, True
	End If
	Set objFSO = Nothing
End Function

Sub ArchiveFolder (zipFile, sFolder)

    With CreateObject("Scripting.FileSystemObject")
        zipFile = .GetAbsolutePathName(zipFile)
        sFolder = .GetAbsolutePathName(sFolder)

        With .CreateTextFile(zipFile, True)
            .Write Chr(80) & Chr(75) & Chr(5) & Chr(6) & String(18, chr(0))
        End With
    End With

    With CreateObject("Shell.Application")
        .NameSpace(zipFile).CopyHere .NameSpace(sFolder).Items

        Do Until .NameSpace(zipFile).Items.Count = _
                 .NameSpace(sFolder).Items.Count
            'WScript.Sleep 1000 
        Loop
    End With

End Sub

Sub RunTest(TDOutput, Debug, CurrentTestSet, CurrentTSTest, CurrentRun, ThisTest, ID)
	On Error Resume Next
	
	' clear output window
	TDOutput.Clear
	
	status = "Passed"
	res = 0
	
	DeleteFolder OUTPUT_DIR
	
	' Run "cmd" application
	res = XTools.run("java", "-Dfile.encoding=UTF-8 -DBmpApplication.url=""https://toon:HullyGully@verbundcloud.bmptest.de"" -jar """ & TEST_JAR & """ -usedefaultlisteners false -d """ & OUTPUT_DIR & """ -suitename """ & ID & """ -testname """ & ID & """ -qcid """ & ID & """", -1)
	output = TDOutput.Text
	
	If res <> 0 Then
		status = "Failed"
	End If
		
	TDOutput.Print status
	
	If Not Debug Then
		
		TDHelper.AddStepToRun "autorun", "", "", output, status
		
		' update steps
		Steps.Post
	
		CurrentRun.Status = status
		CurrentTSTest.Status = status
	
		ArchiveFolder OUTPUT_FILE, OUTPUT_DIR	
		TDHelper.UpLoadAttachment OUTPUT_FILE, CurrentRun	
	
	End If
	
End Sub


