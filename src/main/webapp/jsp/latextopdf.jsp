<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<form action="latextopdf" method="post">
		<input type="text" name="compiler" value="latex" />

		<textarea name="texFile" rows="50" cols="100"></textarea>

		<input type="submit" />
	</form>
</body>
</html>