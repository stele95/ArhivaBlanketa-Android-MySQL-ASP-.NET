using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class SlusaID
    {
        public virtual Korisnik Korisnik { get; set; }
        public virtual Predmet Predmet { get; set; }

        public override bool Equals(object obj)
        {
            if (Object.ReferenceEquals(this, obj))
                return true;

            if (obj.GetType() != typeof(SlusaID))
                return false;

            SlusaID recievedObject = (SlusaID)obj;

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