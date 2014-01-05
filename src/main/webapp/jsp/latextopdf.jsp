<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<form action="latextopdf" method="post">
		<select name="compiler">
			<option value="latex">latex</option>
			<option value="xelatex">xelatex</option>
			<option value="lualatex" selected="selected">lualatex</option>
		</select>

		<textarea name="texFile" rows="50" cols="100"></textarea>

		<input type="submit" />
	</form>
</body>
</html>
