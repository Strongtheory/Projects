#include "solarsystem.h"
#include <QSharedData>

class SolarSystemData : public QSharedData {
public:
    QString name;
    QMap<QString, Planet> planets;
    QColor color;
    int x;
    int y;
};

SolarSystem::SolarSystem(QString name) : data(new SolarSystemData)
{
    data->name = name;
    data->x = qrand() % 1000;
    data->y = qrand() % 1000;
    data->color = QColor(qrand() % 256, qrand() % 256, qrand() % 256, 96);

    QStringList planetNames;
    planetNames.append(QStringLiteral("6 Echo"));
    planetNames.append(QStringLiteral("Cybertron"));
    planetNames.append(QStringLiteral("Curie 3"));
    planetNames.append(QStringLiteral("Nzinga"));
    planetNames.append(QStringLiteral("Lithios"));
    planetNames.append(QStringLiteral("Reach"));
    planetNames.append(QStringLiteral("Halo"));
    planetNames.append(QStringLiteral("Reconciliation"));
    planetNames.append(QStringLiteral("New Mombasa"));
    planetNames.append(QStringLiteral("Draconis"));
    planetNames.append(QStringLiteral("Pegasus"));
    planetNames.append(QStringLiteral("Zeta"));
    planetNames.append(QStringLiteral("Hellespont"));
    planetNames.append(QStringLiteral("Jericho"));
    planetNames.append(QStringLiteral("Hydra"));
    planetNames.append(QStringLiteral("Alpha"));
    planetNames.append(QStringLiteral("Librae"));
    planetNames.append(QStringLiteral("Legit"));
    planetNames.append(QStringLiteral("Halcyon"));
    planetNames.append(QStringLiteral("Ceres"));
    planetNames.append(QStringLiteral("Gaia"));
    planetNames.append(QStringLiteral("Cronus"));
    planetNames.append(QStringLiteral("Metis"));
    planetNames.append(QStringLiteral("Pandora"));
    planetNames.append(QStringLiteral("Moonbase Alpha"));
    planetNames.append(QStringLiteral("Eris"));
    planetNames.append(QStringLiteral("Demetris"));
    planetNames.append(QStringLiteral("Threshold"));
    planetNames.append(QStringLiteral("Harvest"));
    planetNames.append(QStringLiteral("Arcadia"));
    planetNames.append(QStringLiteral("Installation 04"));
    planetNames.append(QStringLiteral("Onyx"));
    planetNames.append(QStringLiteral("Origin"));
    planetNames.append(QStringLiteral("Cryptum"));
    planetNames.append(QStringLiteral("Glasslands"));
    planetNames.append(QStringLiteral("Escalation"));
    planetNames.append(QStringLiteral("Cole Protocol"));
    planetNames.append(QStringLiteral("Midlothian"));
    planetNames.append(QStringLiteral("Valar"));
    planetNames.append(QStringLiteral("Arda"));
    planetNames.append(QStringLiteral("Castleguard"));
    planetNames.append(QStringLiteral("Midgard"));
    planetNames.append(QStringLiteral("Earendel"));
    planetNames.append(QStringLiteral("Middle Earth"));
    planetNames.append(QStringLiteral("Earth"));
    planetNames.append(QStringLiteral("Melkor"));
    planetNames.append(QStringLiteral("Eru"));
    planetNames.append(QStringLiteral("Silmarillion"));
    planetNames.append(QStringLiteral("Illuvatar"));
    planetNames.append(QStringLiteral("Valinor"));
    planetNames.append(QStringLiteral("Luthien"));
    planetNames.append(QStringLiteral("Arnor"));
    planetNames.append(QStringLiteral("Isildur"));
    planetNames.append(QStringLiteral("Pelennor"));
    planetNames.append(QStringLiteral("Arwen"));
    planetNames.append(QStringLiteral("Numenor"));
    planetNames.append(QStringLiteral("Narsil"));
    planetNames.append(QStringLiteral("Barahir"));
    planetNames.append(QStringLiteral("Denethor"));
    planetNames.append(QStringLiteral("Tinuviel"));
    planetNames.append(QStringLiteral("Thranduil"));
    planetNames.append(QStringLiteral("Harad"));
    planetNames.append(QStringLiteral("Caradhras"));
    planetNames.append(QStringLiteral("Anduin"));
    planetNames.append(QStringLiteral("Lothlorien"));
    planetNames.append(QStringLiteral("Rauros"));
    planetNames.append(QStringLiteral("Dunharrow"));
    planetNames.append(QStringLiteral("Telcontar"));
    planetNames.append(QStringLiteral("Haradrim"));
    planetNames.append(QStringLiteral("Eldarion"));
    planetNames.append(QStringLiteral("Easterlings"));
    planetNames.append(QStringLiteral("Ithilien"));
    planetNames.append(QStringLiteral("Orthanc"));
    planetNames.append(QStringLiteral("Dunadan"));
    planetNames.append(QStringLiteral("Evinyatar"));
    planetNames.append(QStringLiteral("Qatar"));
    planetNames.append(QStringLiteral("Estel"));
    planetNames.append(QStringLiteral("Cirdan"));
    planetNames.append(QStringLiteral("Narya"));
    planetNames.append(QStringLiteral("Aelia"));
    planetNames.append(QStringLiteral("Aquila"));
    planetNames.append(QStringLiteral("Atilius"));
    planetNames.append(QStringLiteral("Aulus"));
    planetNames.append(QStringLiteral("Avilius"));
    planetNames.append(QStringLiteral("Avitus"));
    planetNames.append(QStringLiteral("Cassia"));
    planetNames.append(QStringLiteral("Celsus"));
    planetNames.append(QStringLiteral("Drusa"));
    planetNames.append(QStringLiteral("Ennius"));
    planetNames.append(QStringLiteral("Felix"));
    planetNames.append(QStringLiteral("Gallus"));
    planetNames.append(QStringLiteral("Hadrianus"));
    planetNames.append(QStringLiteral("Horatia"));
    planetNames.append(QStringLiteral("Livia"));
    planetNames.append(QStringLiteral("Nerva"));
    planetNames.append(QStringLiteral("Nova"));
    planetNames.append(QStringLiteral("Quintus"));
    planetNames.append(QStringLiteral("Seneca"));
    planetNames.append(QStringLiteral("Sergius"));
    planetNames.append(QStringLiteral("Tacitus"));
    planetNames.append(QStringLiteral("Tiber"));
    planetNames.append(QStringLiteral("Valeria"));
    planetNames.append(QStringLiteral("Valhalla"));
    planetNames.append(QStringLiteral("Narnia"));
    planetNames.append(QStringLiteral("Constantine"));
    planetNames.append(QStringLiteral("Vita"));
    planetNames.append(QStringLiteral("Mithril"));
    planetNames.append(QStringLiteral("Istari"));
    planetNames.append(QStringLiteral("Ichitari"));
    planetNames.append(QStringLiteral("Erebor"));
    planetNames.append(QStringLiteral("Don Guldur"));
    planetNames.append(QStringLiteral("Thrain"));
    planetNames.append(QStringLiteral("Gladden"));
    planetNames.append(QStringLiteral("Bree"));
    planetNames.append(QStringLiteral("Glamdring"));
    planetNames.append(QStringLiteral("Beorn"));
    planetNames.append(QStringLiteral("Gwaihir"));
    planetNames.append(QStringLiteral("Mearas"));
    planetNames.append(QStringLiteral("Grima"));
    planetNames.append(QStringLiteral("Shadowfax"));
    planetNames.append(QStringLiteral("Tirith"));
    planetNames.append(QStringLiteral("Angmar"));
    planetNames.append(QStringLiteral("Imrahil"));
    planetNames.append(QStringLiteral("Rath"));
    planetNames.append(QStringLiteral("Dinen"));
    planetNames.append(QStringLiteral("Orodruin"));
    planetNames.append(QStringLiteral("Undying"));
    planetNames.append(QStringLiteral("Sotheby"));
    planetNames.append(QStringLiteral("Dolomite"));
    planetNames.append(QStringLiteral("Dale"));

    QStringList chosenPlanetNames;
    int numPlanets = qrand() % 3 + 1;
    while (chosenPlanetNames.length() < numPlanets) {
        QString planetName = planetNames.at(qrand() % planetNames.length());
        if (!chosenPlanetNames.contains(planetName)) {
            chosenPlanetNames.append(planetName);
        }
    }

    for (int i = 0; i < numPlanets; i++) {
        data->planets.insert(chosenPlanetNames.at(i), Planet(chosenPlanetNames.at(i)));
    }
}

SolarSystem::SolarSystem(QString name, int x, int y, QMap<QString, Planet> planets)
    : data(new SolarSystemData)
{
    data->name = name;
    data->x = x;
    data->y = y;
    data->planets = planets;
    data->color = QColor(qrand() % 256, qrand() % 256, qrand() % 256, 48);
}

SolarSystem::SolarSystem(const SolarSystem &rhs) :
    QObject(),
    data(rhs.data)
{
}

QString SolarSystem::getName() const {
    return data->name;
}

Planet SolarSystem::getPlanet(QString planetName) const {
    return data->planets.value(planetName);
}

QMap<QString, Planet> SolarSystem::getPlanets() const {
    return data->planets;
}

int SolarSystem::getX() const {
    return data->x;
}

int SolarSystem::getY() const {
    return data->y;
}

QColor SolarSystem::getColor() const {
    return data->color;
}

SolarSystem &SolarSystem::operator=(const SolarSystem &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

SolarSystem::~SolarSystem()
{
}

bool operator==(const SolarSystem solarSystem1, const SolarSystem solarSystem2) {
    return solarSystem1.getName() == solarSystem2.getName()
            && solarSystem1.getX() == solarSystem2.getX()
            && solarSystem1.getY() == solarSystem2.getY();
}

QDataStream& operator<<(QDataStream& stream, const SolarSystem solarSystem) {
    stream << solarSystem.getName();
    stream << solarSystem.getX();
    stream << solarSystem.getY();
    stream << solarSystem.getPlanets();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, SolarSystem& solarSystem) {
    QString name;
    int x;
    int y;
    QMap<QString, Planet> planets;

    stream >> name;
    stream >> x;
    stream >> y;
    stream >> planets;

    solarSystem = SolarSystem(name, x, y, planets);

    return stream;
}
