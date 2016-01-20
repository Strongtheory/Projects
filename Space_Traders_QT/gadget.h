#ifndef GADGET_H
#define GADGET_H

#include <QObject>

#include <QExplicitlySharedDataPointer>
#include "planet.h"

class GadgetData;

class Gadget : public QObject
{
public:

    enum GadgetType {
        Unknown = -1,
        ExtraCargo,
        NavigationSystem,
        AutoRepairSystem,
        TargetingSystem,
        CloakingDevice,
        EscapePod,

        SIZE_GADGET_TYPE
    };

    Gadget(GadgetType type = Unknown);
    Gadget(const Gadget &);
    Gadget &operator=(const Gadget &);
    ~Gadget();

    GadgetType getType() const;
    QString getName() const;
    int getPrice() const;
    Planet::TechLevel getMinimumTechLevel() const;

private:
    QExplicitlySharedDataPointer<GadgetData> data;
};

bool operator==(const Gadget& gadget1, const Gadget& gadget2);
uint qHash(const Gadget& gadget);

QDataStream& operator<<(QDataStream& stream, const Gadget gadget);
QDataStream& operator>>(QDataStream& stream, Gadget& gadget);

QDataStream& operator<<(QDataStream& stream, const Gadget::GadgetType gadgetType);
QDataStream& operator>>(QDataStream& stream, Gadget::GadgetType& gadgetType);

#endif // GADGET_H
