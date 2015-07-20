
// OUTTER HTML

( function( $ ) {

	$.fn.model = function( m ) {
		return ( typeof( m ) == 'undefined' ) ?
			this.data( 'model' ) :
			this.data( 'model', m ) ;
	}

} )( jQuery ) ;
