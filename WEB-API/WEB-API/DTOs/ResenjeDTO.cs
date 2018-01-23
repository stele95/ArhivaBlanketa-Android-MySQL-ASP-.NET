using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class ResenjeDTO
    {
        public virtual int IdResenje { get; set; }
        public virtual BlanketDTO Blanket { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual KorisnikDTO Dodao { get; set; }
        public virtual byte[] ThumbnailInBytes { get; set; }
    }
}