Skullbone = { model_only : false } ;

( function( ) {

	Skullbone.Model = Backbone.Model.extend( { } ) ;

	Skullbone.Collection = Backbone.Collection.extend( { } ) ;

	Skullbone.View = Backbone.View.extend( {

		become : function( parent ) {
			this.setElement( parent ) ;
			this.update( ) ;
			return this ;
		},

		replace : function( parent ) {
			parent.replaceWith( this.$el ) ;
			return this ;
		},

		render : function( parent ) {
			this.update( ) ;
			parent.append( this.$el ) ;
			return this ;
		},

		append : function( child ) {
			child.render( this.$el ) ;
			return this ;
		},

		update : function( ) { },

		setElement : function( element ) {
			if( this.$el ) {
				this.$el.data( 'model', null ) ;
			}
			Backbone.View.prototype.setElement.call( this, element ) ;
			this.$el.data( 'model', this.model ) ;
		},

		make : function( ) {
			var el = Backbone.View.prototype.make.apply( this, arguments ) ;
			$( el ).data( 'model', this.model ) ;
			return el ;
		}

	} ) ;


	Skullbone.Controller = {

		extend : function( parts ) {
			var controller = function( args ) {
				_.extend( this, parts, args ) ;
				if( this.initialize ) {
					this.initialize.call( this, args ) ;
				}
			} ;
			_.extend( controller, parts ) ;

			controller.prototype = this ;
			controller.extend = Skullbone.Controller.extend ;

			return controller ;
		},

	} ;


	Skullbone.Class = {

		extend : function( parts ) {

			if( !parts.Model ) {
				parts.Model = Backbone.Model.extend( { } ) ;
			}
			var model = parts.Model.extend( {}, parts ) ;

			if( !Skullbone.model_only ) {

				if( parts.View ) {
					model.prototype.initialize = _.wrap( model.prototype.initialize, function( init ) {
						init.apply( this, Array.prototype.slice.call( arguments, 1 ) ) ;
						this.view = new parts.View( { model : this } ) ;
					} ) ;
				}

				if( parts.Controller ) {
					model.prototype.initialize = _.wrap( model.prototype.initialize, function( init ) {
						init.apply( this, Array.prototype.slice.call( arguments, 1 ) ) ;
						this.controller = new parts.Controller( { model : this, view : this.view } ) ;
					} ) ;
				}

			}

			model.prototype.delegate = function( object ) {
				var func_names = Array.prototype.slice.call( arguments, 1 ) ;

				_( func_names ).each( function( name ) {
					this[name] = _.bind( object[name], object ) ;
				}, this ) ;
			}

			return model ;
		}

	} ;

} )( ) ;
