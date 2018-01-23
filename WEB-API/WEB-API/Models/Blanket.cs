using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Blanket
    {
        public virtual int IdBlanket { get; set; }
        public virtual Rok Rok { get; set; }
        public virtual int Godina { get; set; }
        public virtual Predmet Predmet { get; set; }
        public virtual DateTime? Datum { get; set; }
        public virtual Korisnik Dodao { get; set; }
        public virtual Korisnik Odobrio { get; set; }
        public virtual bool Odobren { get; set; }
        public virtual bool Pismeni { get; set; }
        public virtual bool Usmeni { get; set; }
        public virtual byte[] ImageInBytes { get; set; }
        //public virtual Image Image { get; set; }
        public virtual byte[] ThumbnailInBytes { get; set; }
        //public virtual Image Thumbnail { get; set; }

        public Image byteArrayToImage(byte[] byteArrayIn)
        {
            MemoryStream ms = new MemoryStream(byteArrayIn);
            ms.Position = 0;
            Image returnImage = Image.FromStream(ms);
            return returnImage;
        }

        public byte[] imageToByte(Image img)
        {
            using (var ms = new MemoryStream())
            {
                img.Save(ms, img.RawFormat);
                return ms.ToArray();
            }
        }
    }
}