#include "weapon.h"
#include <QSharedData>
#include <QHash>
#include <QDataStream>

class WeaponData : public QSharedData {
public:
    Weapon::WeaponType type;
    QString name;
    int price;
    int shipDamage;
    int shieldDamage;
};

Weapon::Weapon(Weapon::WeaponType type) : data(new WeaponData)
{
    data->type = type;
    if (type == Weapon::Pulse) {
        data->name = "Pulse";
        data->price = 100;
        data->shipDamage = 100;
        data->shieldDamage = 100;
    } else if (type == Weapon::Beam) {
        data->name = "Beam";
        data->price = 200;
        data->shipDamage = 50;
        data->shieldDamage = 300;
    } else if (type == Weapon::Missile) {
        data->name = "Missile";
        data->price = 300;
        data->shipDamage = 300;
        data->shieldDamage = 100;
    }
}

Weapon::Weapon(const Weapon &rhs) : data(rhs.data)
{
}

Weapon::WeaponType Weapon::getType() const {
    return data->type;
}

QString Weapon::getName() const {
    return data->name;
}

int Weapon::getPrice() const {
    return data->price;
}

int Weapon::getShipDamage() const {
    return data->shipDamage;
}

int Weapon::getShieldDamage() const {
    return data->shieldDamage;
}

Weapon &Weapon::operator=(const Weapon &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

bool operator==(const Weapon& weapon1, const Weapon& weapon2) {
    return weapon1.getType() == weapon2.getType();
}

uint qHash(const Weapon& weapon) {
    return qHash(weapon.getType());
}

QDataStream& operator<<(QDataStream& stream, const Weapon weapon) {
    stream << weapon.getType();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Weapon& weapon) {
    Weapon::WeaponType weaponType;

    stream >> weaponType;

    weapon = Weapon(weaponType);

    return stream;
}

QDataStream& operator<<(QDataStream& stream, const Weapon::WeaponType weaponType) {
    stream << (int)weaponType;

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Weapon::WeaponType& weaponType) {
    int weaponTypeInt;

    stream >> weaponTypeInt;

    weaponType = static_cast<Weapon::WeaponType>(weaponTypeInt);

    return stream;
}

Weapon::~Weapon()
{
}
