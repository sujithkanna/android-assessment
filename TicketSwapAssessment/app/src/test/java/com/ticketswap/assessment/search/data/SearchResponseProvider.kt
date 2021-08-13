package com.ticketswap.assessment.search.data

import com.squareup.moshi.Moshi
import com.ticketswap.assessment.models.SearchResponse

fun getSearchResponse(): SearchResponse? {
    return Moshi.Builder().build().adapter(SearchResponse::class.java).fromJson(SEARCH_RESPONSE)
}

const val SEARCH_RESPONSE = """
    {
  "artists" : {
    "href" : "https://api.spotify.com/v1/search?query=hello&type=artist&offset=0&limit=1",
    "items" : [ {
      "external_urls" : {
        "spotify" : "https://open.spotify.com/artist/12CmFAwzxYnVtJgnzIysvm"
      },
      "followers" : {
        "href" : null,
        "total" : 135165
      },
      "genres" : [ "anime rock", "j-poprock", "j-rock", "okinawan pop" ],
      "href" : "https://api.spotify.com/v1/artists/12CmFAwzxYnVtJgnzIysvm",
      "id" : "12CmFAwzxYnVtJgnzIysvm",
      "images" : [ {
        "height" : 640,
        "url" : "https://i.scdn.co/image/ab6761610000e5ebe1a29be41a27f152e54fd339",
        "width" : 640
      }, {
        "height" : 320,
        "url" : "https://i.scdn.co/image/ab67616100005174e1a29be41a27f152e54fd339",
        "width" : 320
      }, {
        "height" : 160,
        "url" : "https://i.scdn.co/image/ab6761610000f178e1a29be41a27f152e54fd339",
        "width" : 160
      } ],
      "name" : "Hello Sleepwalkers",
      "popularity" : 53,
      "type" : "artist",
      "uri" : "spotify:artist:12CmFAwzxYnVtJgnzIysvm"
    } ],
    "limit" : 1,
    "next" : "https://api.spotify.com/v1/search?query=hello&type=artist&offset=1&limit=1",
    "offset" : 0,
    "previous" : null,
    "total" : 812
  },
  "tracks" : {
    "href" : "https://api.spotify.com/v1/search?query=hello&type=track&offset=0&limit=1",
    "items" : [ {
      "album" : {
        "album_type" : "compilation",
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/0LyfQWJT6nXafLPZqxe9Of"
          },
          "href" : "https://api.spotify.com/v1/artists/0LyfQWJT6nXafLPZqxe9Of",
          "id" : "0LyfQWJT6nXafLPZqxe9Of",
          "name" : "Various Artists",
          "type" : "artist",
          "uri" : "spotify:artist:0LyfQWJT6nXafLPZqxe9Of"
        } ],
        "available_markets" : [ "AE", "AL", "AR", "AT", "AU", "BA", "BE", "BG", "BH", "BO", "BR", "BY", "CA", "CH", "CI", "CL", "CM", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "DZ", "EC", "EE", "EG", "ES", "FI", "FR", "GB", "GH", "GR", "GT", "HK", "HN", "HR", "HU", "ID", "IE", "IL", "IN", "IS", "IT", "JP", "KE", "KH", "KR", "KW", "LB", "LK", "LT", "LU", "LV", "MA", "ME", "MK", "MT", "MX", "MY", "NG", "NI", "NL", "NO", "NZ", "OM", "PA", "PE", "PH", "PL", "PT", "PY", "QA", "RO", "RS", "RU", "SA", "SE", "SG", "SI", "SK", "SN", "SV", "TH", "TN", "TR", "TT", "TW", "UA", "US", "UY", "VN", "XK", "ZA" ],
        "external_urls" : {
          "spotify" : "https://open.spotify.com/album/1yqIjGFVu9ArrY3c79z2E6"
        },
        "href" : "https://api.spotify.com/v1/albums/1yqIjGFVu9ArrY3c79z2E6",
        "id" : "1yqIjGFVu9ArrY3c79z2E6",
        "images" : [ {
          "height" : 640,
          "url" : "https://i.scdn.co/image/ab67616d0000b2739ce96ccdddcef15baa773db7",
          "width" : 640
        }, {
          "height" : 300,
          "url" : "https://i.scdn.co/image/ab67616d00001e029ce96ccdddcef15baa773db7",
          "width" : 300
        }, {
          "height" : 64,
          "url" : "https://i.scdn.co/image/ab67616d000048519ce96ccdddcef15baa773db7",
          "width" : 64
        } ],
        "name" : "Baila y Canta Regueaton",
        "release_date" : "2021-07-30",
        "release_date_precision" : "day",
        "total_tracks" : 33,
        "type" : "album",
        "uri" : "spotify:album:1yqIjGFVu9ArrY3c79z2E6"
      },
      "artists" : [ {
        "external_urls" : {
          "spotify" : "https://open.spotify.com/artist/790FomKkXshlbRYZFtlgla"
        },
        "href" : "https://api.spotify.com/v1/artists/790FomKkXshlbRYZFtlgla",
        "id" : "790FomKkXshlbRYZFtlgla",
        "name" : "KAROL G",
        "type" : "artist",
        "uri" : "spotify:artist:790FomKkXshlbRYZFtlgla"
      }, {
        "external_urls" : {
          "spotify" : "https://open.spotify.com/artist/1i8SpTcr7yvPOmcqrbnVXY"
        },
        "href" : "https://api.spotify.com/v1/artists/1i8SpTcr7yvPOmcqrbnVXY",
        "id" : "1i8SpTcr7yvPOmcqrbnVXY",
        "name" : "Ozuna",
        "type" : "artist",
        "uri" : "spotify:artist:1i8SpTcr7yvPOmcqrbnVXY"
      } ],
      "available_markets" : [ "AE", "AL", "AR", "AT", "AU", "BA", "BE", "BG", "BH", "BO", "BR", "BY", "CA", "CH", "CI", "CL", "CM", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "DZ", "EC", "EE", "EG", "ES", "FI", "FR", "GB", "GH", "GR", "GT", "HK", "HN", "HR", "HU", "ID", "IE", "IL", "IN", "IS", "IT", "JP", "KE", "KH", "KR", "KW", "LB", "LK", "LT", "LU", "LV", "MA", "ME", "MK", "MT", "MX", "MY", "NG", "NI", "NL", "NO", "NZ", "OM", "PA", "PE", "PH", "PL", "PT", "PY", "QA", "RO", "RS", "RU", "SA", "SE", "SG", "SI", "SK", "SN", "SV", "TH", "TN", "TR", "TT", "TW", "UA", "US", "UY", "VN", "XK", "ZA" ],
      "disc_number" : 1,
      "duration_ms" : 194933,
      "explicit" : false,
      "external_ids" : {
        "isrc" : "USUM71610032"
      },
      "external_urls" : {
        "spotify" : "https://open.spotify.com/track/3VQmOOHBjS7YclT3sfetAU"
      },
      "href" : "https://api.spotify.com/v1/tracks/3VQmOOHBjS7YclT3sfetAU",
      "id" : "3VQmOOHBjS7YclT3sfetAU",
      "is_local" : false,
      "name" : "Hello",
      "popularity" : 4,
      "preview_url" : null,
      "track_number" : 27,
      "type" : "track",
      "uri" : "spotify:track:3VQmOOHBjS7YclT3sfetAU"
    } ],
    "limit" : 1,
    "next" : "https://api.spotify.com/v1/search?query=hello&type=track&offset=1&limit=1",
    "offset" : 0,
    "previous" : null,
    "total" : 95801
  }
}
"""