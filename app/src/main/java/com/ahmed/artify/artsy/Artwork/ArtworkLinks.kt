package com.ahmed.artify.artsy.Artwork

data class ArtworkLinks(val thumbnail: Href,
                        val image: TemplatedHref,
                        val partner: Href,
                        val self: Href,
                        val permalink: Href,
                        val genes: Href,
                        val artists: Href,
                        val similar_artworks: Href,
                        val collection_users: Href,
                        val sale_artworks: Href)
