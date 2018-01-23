using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WEB_API.DataProviders;
using WEB_API.DTOs;

namespace WEB_API.Controllers.Api
{
    public class KomentarDTOController : ApiController
    {

        // GET api/KomentarDTO/{id}?blanket={blanket}
        public IEnumerable<KomentarDTO> Get(int id, bool blanket)
        {
            KomentarDataProvider provider = new KomentarDataProvider();

            return provider.GetKomentarDTO(id, blanket);
            
        }
    }
}
