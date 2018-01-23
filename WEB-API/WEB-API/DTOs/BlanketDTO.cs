using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class BlanketDTO
    {
        public virtual int IdBlanket { get; set; }
        public virtual RokDTO Rok { get; set; }
        public virtual int Godina { get; set; }
        public virtual PredmetDTO Predmet { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual KorisnikDTO Dodao { get; set; }
        public virtual KorisnikDTO Odobrio { get; set; }
        public virtual bool Odobren { get; set; }
        public virtual bool Pismeni { get; set; }
        public virtual bool Usmeni { get; set; }
        public virtual byte[] ThumbnailInBytes { get; set; }
    }
}