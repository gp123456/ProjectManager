
var Parser = {

	fromResult : function( data, format, type ) {

		var object = {}

		var pattern = /(\w+)(\*|\.\.\.)?(\((\w+)\))?/ ;

		_( format[type] ).each( function( field, i ) {

			var match = pattern.exec( field ) ;

			var field_name     = match[1] ;
			var field_is_array = match[2] == '*' ;
			var field_is_rest  = match[2] == '...' ;
			var field_type     = match[4] ;

			var field_value ;

			if( field_is_array && field_type ) {
				field_value = [] ;

				_( data[i] ).each( function( datum ) {
					field_value.push( Parser.fromResult( datum, format, field_type ) )
				} ) ;
			}
			else if( field_is_rest && field_type ) {
				field_value = [] ;
				_( data.slice( i ) ).each( function( datum ) {
					field_value.push( Parser.fromResult( datum, format, field_type ) )
				} ) ;
			}
			else if( field_is_rest ) {
				field_value = data.slice( i ) ;
			}
			else if( field_type ) {
				field_value = Parser.fromResult( data[i], format, field_type ) ;
			}
			else {
				field_value = data[i] ;
			}

			object[field_name] = field_value ;

		}, this ) ;

		return object ;
	},


	toResult : function( data, format, type ) {

		var object = []

		_( format[type] ).each( function( field, i ) {

			var pattern = /(\w+)(\*)?(\((\w+)\))?/ ;
			var match = pattern.exec( field ) ;

			var field_name     = match[1] ;
			var field_is_array = match[2] ;
			var field_type     = match[4] ;

			var field_value ;

			if( field_is_array && field_type ) {
				field_value = [] ;

				_( data[i] ).each( function( datum ) {
					field_value.push( Parser.toResult( datum, format, field_type ) )
				} ) ;
			}
			else if( field_type ) {
				field_value = Parser.toResult( data[i], format, field_type ) ;
			}
			else {
				field_value = data[field_name] ;
			}

			object[i] = field_value ;

		}, this ) ;

		return object ;

	}

} ;
