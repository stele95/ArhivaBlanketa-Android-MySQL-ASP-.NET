using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class SlusaIDDTO
    {
        public virtual KorisnikDTO Korisnik { get; set; }
        public virtual PredmetDTO Predmet { get; set; }

        public override bool Equals(object obj)
        {
            if (Object.ReferenceEquals(this, obj))
                return true;

            if (obj.GetType() != typeof(SlusaIDDTO))
                return false;

            SlusaIDDTO recievedObject = (SlusaIDDTO)obj;

            if ((Korisnik.IdKorisnik == recievedObject.Korisnik.IdKorisnik) &&
                (Predmet.IdPredmet == recievedObject.Predmet.IdPredmet))
            {
                return true;
            }

            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}