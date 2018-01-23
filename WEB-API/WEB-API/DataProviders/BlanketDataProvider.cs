using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Linq;
using NHibernate;
using WEB_API.Models;
using WEB_API.DTOs;
using AutoMapper;

namespace WEB_API.DataProviders
{
    public class BlanketDataProvider
    {
        public IEnumerable<Blanket> GetBlanket()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Blanket> k = s.Query<Blanket>().Select(r => r);
            /*foreach(Blanket b in k)
            {
                b.Image = b.byteArrayToImage(b.ImageInBytes);
            }*/

            return k;
        }

        public Blanket GetBlanket(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Blanket>().Where(r => r.IdBlanket == id).Select(p => p).FirstOrDefault();

        }

        public BlanketDTO GetBlanketDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Blanket d = s.Query<Blanket>().Where(r => r.IdBlanket == id).Select(p => p).FirstOrDefault();

            if (d == null)
            { return null; }


            return Mapper.Map<Blanket, BlanketDTO>(d);
        }

        public Blanket AddBlanket(Blanket r)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(r);
                s.Flush();
                Blanket b=s.Query<Blanket>()
                    .Where(c=>c.Rok.IdRok==r.Rok.IdRok).Where(c=>c.Predmet.IdPredmet==r.Predmet.IdPredmet)
                    .Where(c=>c.Godina==r.Godina).Where(c=>c.Datum==r.Datum)
                    .Where(c=>c.Pismeni==r.Pismeni).Where(c=>c.Usmeni==r.Usmeni).FirstOrDefault();
                s.Close();
                return b;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Blanket RemoveBlanket(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Blanket r = s.Load<Blanket>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetBlanket(id);
            }
            catch (Exception ex)
            {
                return new Blanket();
            }
        }

        public Blanket UpdateBlanket(int id, Blanket b)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Blanket r = s.Load<Blanket>(id);
                r.Datum = b.Datum;
                r.Dodao = b.Dodao;
                r.Godina = b.Godina;
                r.Odobren = b.Odobren;
                r.Odobrio = b.Odobrio;
                r.Usmeni = b.Usmeni;
                r.Predmet = b.Predmet;
                r.Rok = b.Rok;
                r.ImageInBytes = b.ImageInBytes;
                r.ThumbnailInBytes = b.ThumbnailInBytes;

                //r.ImageInBytes = r.imageToByte(b.Image);

                s.Update(r);
                s.Flush();
                s.Close();
                return GetBlanket(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }


        public Blanket NadjiBlanket(BlanketSearch r)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Blanket b = s.Query<Blanket>()
                    .Where(c => c.Rok.IdRok == r.IdRok).Where(c => c.Predmet.IdPredmet == r.IdPredmet)
                    .Where(c => c.Godina == r.Godina).Where(c => c.Pismeni == r.Pismeni)
                    .Where(c => c.Usmeni == r.Usmeni).FirstOrDefault();
                s.Close();
                return b;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}