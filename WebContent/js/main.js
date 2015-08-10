// The root URL for the RESTful services
var rootURL = "http://localhost:8080/Books/rest/books";

var currentWine;

// Retrieve wine list when application starts 
findAll();

// Nothing to delete in initial application state
$('#btnDelete').hide();

// Register listeners
$('#btnSearch').click(function() {
	search($('#searchKey').val());
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
	if(e.which == 13) {
		search($('#searchKey').val());
		e.preventDefault();
		return false;
    }
});

$('#btnAdd').click(function() {
	newBook();
	return false;
});

$('#btnSave').click(function() {
	if ($('#bookId').val() == '')
		addBook();
	else
		updateBook();
	return false;
});

$('#btnDelete').click(function() {
	deleteBook();
	return false;
});

$('#bookList a').live('click', function() {
	findById($(this).data('identity'));
});


function search(searchKey) {
	if (searchKey == '') 
		findAll();
	else
		findByName(searchKey);
}

function newBook() {
	$('#btnDelete').hide();
	currentBook = {};
	renderDetails(currentBook); // Display empty form
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList
	});
}

function findByName(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderList 
	});
}

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentBook = data;
			renderDetails(currentBook);
		}
	});
}

function addBook() {
	console.log('addBook');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Book created successfully');
			$('#btnDelete').show();
			$('#bookId').val(data.id);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addBook error: ' + textStatus);
		}
	});
}

function updateBook() {
	console.log('updateBook');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL + '/' + $('#bookId').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('book updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateBook error: ' + textStatus);
		}
	});
}

function deleteBook() {
	console.log('deleteBook');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#bookId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Book deleted successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteBook error');
		}
	});
}

function renderList(data) {
	alert("Hii");
	alert(data);
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	alert(list);
	$('#bookList li').remove();
	$.each(list, function(index, book) {
		$('#bookList').append('<li><a href="#" data-identity="' + book.id + '">'+book.book_name+'</a></li>');
	});
}

function renderDetails(book) {
	$('#bookId').val(book.id);
	$('#name').val(book.name);
	$('#pages').val(book.pages);
	$('#date').val(book.date);
	$('#author').val(book.author);
	
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var bookId = $('#bookId').val();
	return JSON.stringify({
		"id": bookId == "" ? null : bookId, 
		"name": $('#name').val(), 
		"grapes": $('#pages').val(),
		"country": $('#date').val(),
		"region": $('#author').val(),
		});
}
